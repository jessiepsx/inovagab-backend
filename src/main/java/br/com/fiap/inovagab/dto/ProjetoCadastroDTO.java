package br.com.fiap.inovagab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProjetoCadastroDTO(

        @NotBlank
        String nome,

        @NotBlank
        String descricao,

        @NotBlank
        String estrategiaId,

        @NotBlank
        String etapa,

        @NotBlank
        String status,

        @NotNull
        Double investimento,

        @NotNull
        LocalDate prazo,

        Double retornoFinanceiro,

        Double progresso

) {
}