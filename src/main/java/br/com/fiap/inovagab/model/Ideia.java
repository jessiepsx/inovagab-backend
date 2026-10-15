package br.com.fiap.inovagab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ideias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ideia {

    @Id
    private String id;

    private String titulo;

    private String descricao;

    /** Problema do dia a dia que motivou a ideia. */
    private String problema;

    /** Ex.: "Ideia de melhoria", "Problema operacional", "Oportunidade". */
    private String tipo;

    /** Ex.: "Logística", "Passageiros", "Comércio", "TI", "RH". */
    private String area;

    /** Ex.: "Alto", "Médio", "Baixo". */
    private String impactoEstimado;

    @Builder.Default
    private StatusIdeia status = StatusIdeia.PENDENTE;

    /** Data/hora de envio da ideia. Preenchida automaticamente na criação. */
    @Builder.Default
    private LocalDateTime data = LocalDateTime.now();

    /** Id do Usuario (operador) que enviou a ideia. */
    private String operadorId;

    /** Nome do operador, copiado no momento da criação. */
    private String operadorNome;

    /** Id da Estrategia vigente à qual a ideia foi vinculada (opcional). */
    private String estrategiaId;

    private Integer notaIA;

    private String prioridadeIA;

    private String justificativaIA;

    private LocalDateTime analisadaEm;
}
