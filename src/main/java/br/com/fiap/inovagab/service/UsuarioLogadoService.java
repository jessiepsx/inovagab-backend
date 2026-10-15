package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.exception.AcessoNegadoException;
import br.com.fiap.inovagab.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogadoService {

    public Usuario getUsuarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof Usuario usuario)) {
            throw new AcessoNegadoException("Nenhum usuário autenticado.");
        }
        return usuario;
    }
}
