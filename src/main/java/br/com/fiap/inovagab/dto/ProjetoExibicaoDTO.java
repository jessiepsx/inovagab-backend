package br.com.fiap.inovagab.dto;

import br.com.fiap.inovagab.model.Projeto;
import java.time.LocalDate;

public record ProjetoExibicaoDTO(
        String id,
        String nome,
        String descricao,
        String estrategiaId,
        String etapa,
        String status,
        Double investimento,
        LocalDate prazo,
        Double retornoFinanceiro,
        Double progresso
) {

    public ProjetoExibicaoDTO(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getEstrategiaId(),
                projeto.getEtapa(),
                projeto.getStatus(),
                projeto.getInvestimento(),
                projeto.getPrazo(),
                projeto.getRetornoFinanceiro(),
                projeto.getProgresso()
        );
    }
}