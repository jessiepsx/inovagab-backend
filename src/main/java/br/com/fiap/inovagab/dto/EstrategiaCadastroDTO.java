package br.com.fiap.inovagab.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Payload de entrada para criação/edição de uma estratégia.
 * Uso exclusivo do perfil LIDER (ver {@code EstrategiaController}).
 */
public record EstrategiaCadastroDTO(

        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotBlank(message = "A categoria é obrigatória")
        String categoria,

        String campanha,

        /** Opcional. Na criação, ausente = true. Na edição, ausente = mantém o valor atual. */
        Boolean ativa
) {
}
