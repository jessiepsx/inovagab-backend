package br.com.fiap.inovagab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ResultadoCadastroDTO(

        @NotBlank
        String projetoId,

        @NotNull
        LocalDate data,

        @NotBlank
        String descricao,

        @NotNull
        Double investimento,

        @NotNull
        Double retornoFinanceiro,

        Double lucro,

        Double aumentoProdutividade,

        Double roi

) {
}