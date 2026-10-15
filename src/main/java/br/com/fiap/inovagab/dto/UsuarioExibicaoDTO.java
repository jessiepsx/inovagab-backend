package br.com.fiap.inovagab.dto;

import br.com.fiap.inovagab.model.Perfil;

public record UsuarioExibicaoDTO(
        String id,
        String nome,
        String email,
        Perfil perfil,
        boolean ativo
) {
}
