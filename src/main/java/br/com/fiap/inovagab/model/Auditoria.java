package br.com.fiap.inovagab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "auditorias")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auditoria {

    @Id
    private String id;

    private String usuarioId;
    private String usuarioNome;
    private Perfil perfil;
    private String acao;

    @Builder.Default
    private LocalDateTime data = LocalDateTime.now();

    private String entidade;
    private String entidadeId;
    private String descricao;
}
