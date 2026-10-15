package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.dto.IdeiaCadastroDTO;
import br.com.fiap.inovagab.dto.IdeiaExibicaoDTO;
import br.com.fiap.inovagab.exception.AcessoNegadoException;
import br.com.fiap.inovagab.exception.IdeiaNaoEncontradaException;
import br.com.fiap.inovagab.exception.RegraDeNegocioException;
import br.com.fiap.inovagab.model.Estrategia;
import br.com.fiap.inovagab.model.Ideia;
import br.com.fiap.inovagab.model.StatusIdeia;
import br.com.fiap.inovagab.model.Usuario;
import br.com.fiap.inovagab.repository.IdeiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IdeiaService {

    private final IdeiaRepository ideiaRepository;
    private final EstrategiaService estrategiaService;
    private final UsuarioLogadoService usuarioLogadoService;

    /** OPERADOR envia uma nova ideia; status inicial é sempre PENDENTE. */
    public IdeiaExibicaoDTO criar(IdeiaCadastroDTO dto) {
        Usuario operador = usuarioLogadoService.getUsuarioAtual();
        validarEstrategiaVigente(dto.estrategiaId());

        Ideia ideia = Ideia.builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .problema(dto.problema())
                .tipo(dto.tipo())
                .area(dto.area())
                .impactoEstimado(dto.impactoEstimado())
                .status(StatusIdeia.PENDENTE)
                .data(LocalDateTime.now())
                .operadorId(operador.getId())
                .operadorNome(operador.getNome())
                .estrategiaId(dto.estrategiaId())
                .build();

        return IdeiaExibicaoDTO.fromEntity(ideiaRepository.save(ideia));
    }

    /** GESTOR/LIDER: todas as ideias, opcionalmente filtradas por status (ex.: PENDENTE na triagem). */
    public List<IdeiaExibicaoDTO> listarTodas(StatusIdeia status) {
        List<Ideia> ideias = status != null
                ? ideiaRepository.findByStatusOrderByDataDesc(status)
                : ideiaRepository.findAllByOrderByDataDesc();
        return ideias.stream().map(IdeiaExibicaoDTO::fromEntity).toList();
    }

    /** OPERADOR: apenas as próprias ideias enviadas. */
    public List<IdeiaExibicaoDTO> listarMinhas() {
        String operadorId = usuarioLogadoService.getUsuarioAtual().getId();
        return ideiaRepository.findByOperadorIdOrderByDataDesc(operadorId).stream()
                .map(IdeiaExibicaoDTO::fromEntity)
                .toList();
    }

    public Ideia buscarEntidade(String id) {
        return ideiaRepository.findById(id)
                .orElseThrow(() -> new IdeiaNaoEncontradaException(id));
    }

    /** OPERADOR edita uma ideia própria, somente enquanto ela ainda estiver PENDENTE. */
    public IdeiaExibicaoDTO atualizar(String id, IdeiaCadastroDTO dto) {
        Ideia ideia = buscarEntidade(id);
        validarDonoEIdeiaPendente(ideia);
        validarEstrategiaVigente(dto.estrategiaId());

        ideia.setTitulo(dto.titulo());
        ideia.setDescricao(dto.descricao());
        ideia.setProblema(dto.problema());
        ideia.setTipo(dto.tipo());
        ideia.setArea(dto.area());
        ideia.setImpactoEstimado(dto.impactoEstimado());
        ideia.setEstrategiaId(dto.estrategiaId());

        return IdeiaExibicaoDTO.fromEntity(ideiaRepository.save(ideia));
    }

    /** OPERADOR exclui uma ideia própria, somente enquanto ela ainda estiver PENDENTE. */
    public void excluir(String id) {
        Ideia ideia = buscarEntidade(id);
        validarDonoEIdeiaPendente(ideia);
        ideiaRepository.delete(ideia);
    }

    /** GESTOR aprova, recusa ou move a ideia para "em projeto". */
    public IdeiaExibicaoDTO atualizarStatus(String id, StatusIdeia novoStatus) {
        Ideia ideia = buscarEntidade(id);
        ideia.setStatus(novoStatus);
        return IdeiaExibicaoDTO.fromEntity(ideiaRepository.save(ideia));
    }

    private void validarEstrategiaVigente(String estrategiaId) {
        Estrategia estrategia = estrategiaService.buscarEntidade(estrategiaId);
        if (!estrategia.isAtiva()) {
            throw new RegraDeNegocioException("A estratégia informada não está vigente (ativa).");
        }
    }

    private void validarDonoEIdeiaPendente(Ideia ideia) {
        String usuarioAtualId = usuarioLogadoService.getUsuarioAtual().getId();
        if (!ideia.getOperadorId().equals(usuarioAtualId)) {
            throw new AcessoNegadoException("Você só pode alterar suas próprias ideias.");
        }
        if (ideia.getStatus() != StatusIdeia.PENDENTE) {
            throw new RegraDeNegocioException("Não é possível alterar uma ideia que já foi avaliada.");
        }
    }
}
