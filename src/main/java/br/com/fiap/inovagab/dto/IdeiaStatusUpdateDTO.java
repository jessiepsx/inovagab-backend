package br.com.fiap.inovagab.dto;

import br.com.fiap.inovagab.model.StatusIdeia;
import jakarta.validation.constraints.NotNull;


public record IdeiaStatusUpdateDTO(

        @NotNull(message = "O novo status é obrigatório")
        StatusIdeia status
) {
}
