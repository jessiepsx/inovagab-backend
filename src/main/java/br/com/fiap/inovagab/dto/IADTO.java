package br.com.fiap.inovagab.dto;

/** Resultado da análise de uma ideia pela IA e item do ranking. */
public record IADTO(
        String ideiaId,
        String titulo,
        Integer score,
        String prioridade,
        String justificativa
) {
}
