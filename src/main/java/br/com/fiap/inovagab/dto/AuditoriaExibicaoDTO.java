package br.com.fiap.inovagab.dto;

import br.com.fiap.inovagab.model.Auditoria;
import br.com.fiap.inovagab.model.Perfil;

import java.time.LocalDateTime;

public record AuditoriaExibicaoDTO(
        String id,
        String usuarioId,
        String usuarioNome,
        Perfil perfil,
        String acao,
        LocalDateTime data,
        String entidade,
        String entidadeId,
        String descricao
) {
    public static AuditoriaExibicaoDTO fromEntity(Auditoria auditoria) {
        return new AuditoriaExibicaoDTO(
                auditoria.getId(),
                auditoria.getUsuarioId(),
                auditoria.getUsuarioNome(),
                auditoria.getPerfil(),
                auditoria.getAcao(),
                auditoria.getData(),
                auditoria.getEntidade(),
                auditoria.getEntidadeId(),
                auditoria.getDescricao()
        );
    }
}
