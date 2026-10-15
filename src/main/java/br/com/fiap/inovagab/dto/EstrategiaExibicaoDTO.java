package br.com.fiap.inovagab.dto;

import br.com.fiap.inovagab.model.Estrategia;

import java.time.LocalDate;

/**
 * Payload de saída (resposta) para consultas de estratégias.
 * Os 3 perfis (OPERADOR, GESTOR, LIDER) podem ler.
 */
public record EstrategiaExibicaoDTO(
        String id,
        String titulo,
        String descricao,
        String categoria,
        String campanha,
        LocalDate data,
        boolean ativa
) {
    public static EstrategiaExibicaoDTO fromEntity(Estrategia estrategia) {
        return new EstrategiaExibicaoDTO(
                estrategia.getId(),
                estrategia.getTitulo(),
                estrategia.getDescricao(),
                estrategia.getCategoria(),
                estrategia.getCampanha(),
                estrategia.getData(),
                estrategia.isAtiva()
        );
    }
}
