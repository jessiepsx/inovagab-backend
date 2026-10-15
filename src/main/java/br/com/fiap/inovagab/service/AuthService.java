package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.dto.LoginDTO;
import br.com.fiap.inovagab.dto.TokenDTO;
import br.com.fiap.inovagab.dto.UsuarioCadastroDTO;
import br.com.fiap.inovagab.dto.UsuarioExibicaoDTO;
import br.com.fiap.inovagab.exception.EmailJaCadastradoException;
import br.com.fiap.inovagab.exception.UsuarioNaoEncontradoException;
import br.com.fiap.inovagab.model.Usuario;
import br.com.fiap.inovagab.repository.UsuarioRepository;
import br.com.fiap.inovagab.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UsuarioExibicaoDTO cadastrar(UsuarioCadastroDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("Já existe um usuário cadastrado com o email: " + dto.email());
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .perfil(dto.perfil())
                .ativo(true)
                .build();

        usuario = usuarioRepository.save(usuario);

        return toExibicaoDTO(usuario);
    }

    public TokenDTO login(LoginDTO dto) {
        // authenticate() dispara BadCredentialsException se email/senha
        // estiverem errados -> tratado pelo GlobalExceptionHandler
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuário não encontrado com o email: " + dto.email()));

        String token = jwtService.gerarToken(usuario.getEmail(), usuario.getPerfil().name(), usuario.getId());

        return new TokenDTO(token, toExibicaoDTO(usuario));
    }

    private UsuarioExibicaoDTO toExibicaoDTO(Usuario usuario) {
        return new UsuarioExibicaoDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.isEnabled()
        );
    }
}
