package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.dto.IADTO;
import br.com.fiap.inovagab.exception.IAIntegracaoException;
import br.com.fiap.inovagab.model.Ideia;
import br.com.fiap.inovagab.repository.IdeiaRepository;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IAService {

    private final IdeiaService ideiaService;
    private final IdeiaRepository ideiaRepository;
    private final AuditoriaService auditoriaService;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key:}")
    private String apiKey;

    @Value("${gemini.model:gemini-3.6-flash}")
    private String model;

    /**
     * Analisa uma ideia cadastrada no MongoDB, consulta a API Gemini e
     * salva score, prioridade e justificativa na própria ideia.
     */
    public IADTO analisarIdeia(String ideiaId) {
        Ideia ideia = ideiaService.buscarEntidade(ideiaId);
        validarConfiguracao();

        RespostaIA resposta = chamarGemini(ideia);

        ideia.setNotaIA(resposta.score());
        ideia.setPrioridadeIA(resposta.prioridade());
        ideia.setJustificativaIA(resposta.justificativa());
        ideia.setAnalisadaEm(LocalDateTime.now());
        ideiaRepository.save(ideia);

        auditoriaService.registrar(
                "ANALISOU_IDEIA_COM_IA",
                "IDEIA",
                ideia.getId(),
                "Ideia classificada pela IA com score " + resposta.score()
                        + " e prioridade " + resposta.prioridade() + "."
        );

        return toDTO(ideia);
    }

    /** Ranking das ideias já analisadas, da maior para a menor nota. */
    public List<IADTO> ranking() {
        return ideiaRepository.findByNotaIAIsNotNullOrderByNotaIADesc().stream()
                .map(this::toDTO)
                .toList();
    }

    private RespostaIA chamarGemini(Ideia ideia) {
        String prompt = """
                Você é um assistente de inovação corporativa do Grupo Águia Branca.
                Analise a ideia abaixo para ajudar um gestor a priorizar iniciativas.

                Critérios: impacto esperado, relevância do problema, clareza da proposta,
                potencial de ganho operacional e aderência ao contexto informado.

                Ideia:
                Título: %s
                Descrição: %s
                Problema: %s
                Tipo: %s
                Área: %s
                Impacto estimado pelo colaborador: %s

                Retorne SOMENTE um JSON válido, sem markdown, exatamente com estes campos:
                {
                  "score": 0,
                  "prioridade": "ALTA",
                  "justificativa": "texto curto"
                }

                Regras:
                - score: inteiro de 0 a 100;
                - prioridade: ALTA, MEDIA ou BAIXA;
                - justificativa: no máximo 3 frases, objetiva e em português.
                """.formatted(
                safe(ideia.getTitulo()),
                safe(ideia.getDescricao()),
                safe(ideia.getProblema()),
                safe(ideia.getTipo()),
                safe(ideia.getArea()),
                safe(ideia.getImpactoEstimado())
        );

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                ),
                "generationConfig", Map.of(
                        "responseMimeType", "application/json",
                        "temperature", 0.2
                )
        );

        try {
            String respostaHttp = RestClient.create()
                    .post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent", model)
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(String.class);

            if (respostaHttp == null || respostaHttp.isBlank()) {
                throw new IAIntegracaoException("A API de IA retornou uma resposta vazia.");
            }

            JsonNode raiz = objectMapper.readTree(respostaHttp);
            JsonNode textNode = raiz.path("candidates").path(0)
                    .path("content").path("parts").path(0).path("text");

            if (textNode.isMissingNode() || textNode.asText().isBlank()) {
                throw new IAIntegracaoException("Não foi possível localizar o conteúdo da resposta da IA.");
            }

            JsonNode resultado = objectMapper.readTree(limparMarkdown(textNode.asText()));

            int score = Math.max(0, Math.min(100, resultado.path("score").asInt()));
            String prioridade = normalizarPrioridade(resultado.path("prioridade").asText(), score);
            String justificativa = resultado.path("justificativa").asText("Análise concluída pela IA.");

            return new RespostaIA(score, prioridade, justificativa);
        } catch (IAIntegracaoException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IAIntegracaoException("Falha ao consultar a API Gemini: " + ex.getMessage(), ex);
        }
    }

    private void validarConfiguracao() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IAIntegracaoException(
                    "GEMINI_API_KEY não configurada. Defina a variável de ambiente antes de usar a análise por IA."
            );
        }
    }

    private String limparMarkdown(String texto) {
        String limpo = texto.trim();
        if (limpo.startsWith("```")) {
            limpo = limpo.replaceFirst("^```(?:json)?\\s*", "");
            limpo = limpo.replaceFirst("\\s*```$", "");
        }
        return limpo.trim();
    }

    private String normalizarPrioridade(String prioridade, int score) {
        if (prioridade != null) {
            String normalizada = prioridade.trim().toUpperCase()
                    .replace("É", "E");
            if (normalizada.equals("ALTA") || normalizada.equals("MEDIA") || normalizada.equals("BAIXA")) {
                return normalizada;
            }
        }
        if (score >= 70) return "ALTA";
        if (score >= 40) return "MEDIA";
        return "BAIXA";
    }

    private IADTO toDTO(Ideia ideia) {
        return new IADTO(
                ideia.getId(),
                ideia.getTitulo(),
                ideia.getNotaIA(),
                ideia.getPrioridadeIA(),
                ideia.getJustificativaIA()
        );
    }

    private String safe(String valor) {
        return valor == null || valor.isBlank() ? "Não informado" : valor;
    }

    private record RespostaIA(int score, String prioridade, String justificativa) {
    }
}
