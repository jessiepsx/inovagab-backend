package br.com.fiap.inovagab.dto;

import br.com.fiap.inovagab.model.Resultado;

import java.time.LocalDate;

public record ResultadoExibicaoDTO(
        String id,
        String projetoId,
        LocalDate data,
        String descricao,
        Double investimento,
        Double retornoFinanceiro,
        Double lucro,
        Double aumentoProdutividade,
        Double roi
) {

    public ResultadoExibicaoDTO(Resultado resultado) {
        this(
                resultado.getId(),
                resultado.getProjetoId(),
                resultado.getData(),
                resultado.getDescricao(),
                resultado.getInvestimento(),
                resultado.getRetornoFinanceiro(),
                resultado.getLucro(),
                resultado.getAumentoProdutividade(),
                resultado.getRoi()
        );
    }
}