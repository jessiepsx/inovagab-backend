package br.com.fiap.inovagab.dto;

public record TokenDTO(
        String token,
        String tipo,
        UsuarioExibicaoDTO usuario
) {
    public TokenDTO(String token, UsuarioExibicaoDTO usuario) {
        this(token, "Bearer", usuario);
    }
}
