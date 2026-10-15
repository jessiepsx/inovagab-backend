package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.dto.AuditoriaExibicaoDTO;
import br.com.fiap.inovagab.model.Auditoria;
import br.com.fiap.inovagab.model.Usuario;
import br.com.fiap.inovagab.repository.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioLogadoService usuarioLogadoService;

    public void registrar(String acao, String entidade, String entidadeId, String descricao) {
        Usuario usuario = usuarioLogadoService.getUsuarioAtual();

        Auditoria auditoria = Auditoria.builder()
                .usuarioId(usuario.getId())
                .usuarioNome(usuario.getNome())
                .perfil(usuario.getPerfil())
                .acao(acao)
                .data(LocalDateTime.now())
                .entidade(entidade)
                .entidadeId(entidadeId)
                .descricao(descricao)
                .build();

        auditoriaRepository.save(auditoria);
    }

    public List<AuditoriaExibicaoDTO> listarRecentes() {
        return auditoriaRepository.findTop50ByOrderByDataDesc().stream()
                .map(AuditoriaExibicaoDTO::fromEntity)
                .toList();
    }
}
