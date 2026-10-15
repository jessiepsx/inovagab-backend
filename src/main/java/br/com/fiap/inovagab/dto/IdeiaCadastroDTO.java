package br.com.fiap.inovagab.dto;

import jakarta.validation.constraints.NotBlank;


public record IdeiaCadastroDTO(

        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotBlank(message = "O problema é obrigatório")
        String problema,

        @NotBlank(message = "O tipo é obrigatório")
        String tipo,

        @NotBlank(message = "A área é obrigatória")
        String area,

        @NotBlank(message = "O impacto estimado é obrigatório")
        String impactoEstimado,

        @NotBlank(message = "A ideia precisa estar vinculada a uma estratégia vigente")
        String estrategiaId
) {
}
