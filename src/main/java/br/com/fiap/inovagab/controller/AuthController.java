package br.com.fiap.inovagab.controller;

import br.com.fiap.inovagab.dto.LoginDTO;
import br.com.fiap.inovagab.dto.TokenDTO;
import br.com.fiap.inovagab.dto.UsuarioCadastroDTO;
import br.com.fiap.inovagab.dto.UsuarioExibicaoDTO;
import br.com.fiap.inovagab.model.Usuario;
import br.com.fiap.inovagab.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioExibicaoDTO> cadastrar(
            @RequestBody @Valid UsuarioCadastroDTO dto
    ) {
        UsuarioExibicaoDTO usuario = authService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(
            @RequestBody @Valid LoginDTO dto
    ) {
        TokenDTO token = authService.login(dto);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioExibicaoDTO> me(
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.ok(
                new UsuarioExibicaoDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getPerfil(),
                        usuario.isEnabled()
                )
        );
    }
}