package br.com.fiap.inovagab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "estrategias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estrategia {

    @Id
    private String id;

    private String titulo;

    private String descricao;

    /** Ex.: "Sustentabilidade", "Eficiência Operacional", "Experiência do Cliente". */
    private String categoria;

    /** Campanha ou iniciativa institucional à qual a diretriz está associada. */
    private String campanha;

    /** Data de publicação da diretriz. Preenchida automaticamente na criação. */
    private LocalDate data;

    /**
     * Indica se a estratégia está vigente. Somente estratégias ativas podem
     * ser vinculadas a novas ideias (ver {@code IdeiaService#criar}).
     */
    @Builder.Default
    private boolean ativa = true;
}
