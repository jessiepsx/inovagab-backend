package br.com.fiap.inovagab.dto;

import java.time.LocalDate;

public record DashboardProjetoDTO(
        String id,
        String nome,
        String descricao,
        String estrategiaId,
        String etapa,
        String status,
        Double investimento,
        Double retornoFinanceiro,
        Double progresso,
        LocalDate prazo,
        Double roi,
        Double aumentoProdutividade
) {
}