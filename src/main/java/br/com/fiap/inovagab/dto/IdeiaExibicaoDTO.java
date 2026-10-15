package br.com.fiap.inovagab.dto;

import br.com.fiap.inovagab.model.Ideia;
import br.com.fiap.inovagab.model.StatusIdeia;

import java.time.LocalDateTime;

/** Payload de saída (resposta) para consultas de ideias. */
public record IdeiaExibicaoDTO(
        String id,
        String titulo,
        String descricao,
        String problema,
        String tipo,
        String area,
        String impactoEstimado,
        StatusIdeia status,
        LocalDateTime data,
        String operadorId,
        String operadorNome,
        String estrategiaId
) {
    public static IdeiaExibicaoDTO fromEntity(Ideia ideia) {
        return new IdeiaExibicaoDTO(
                ideia.getId(),
                ideia.getTitulo(),
                ideia.getDescricao(),
                ideia.getProblema(),
                ideia.getTipo(),
                ideia.getArea(),
                ideia.getImpactoEstimado(),
                ideia.getStatus(),
                ideia.getData(),
                ideia.getOperadorId(),
                ideia.getOperadorNome(),
                ideia.getEstrategiaId()
        );
    }
}
