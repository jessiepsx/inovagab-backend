package br.com.fiap.inovagab.dto;

import java.time.LocalDateTime;

public record ErroResponseDTO(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem
) {
}
