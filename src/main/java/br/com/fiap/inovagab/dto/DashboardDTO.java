package br.com.fiap.inovagab.dto;

import java.util.List;
import java.util.Map;

public record DashboardDTO(
        Long quantidadeProjetos,
        Double investimentoTotal,
        Double retornoFinanceiroTotal,
        Double lucroTotal,
        Double roiGeral,
        Double aumentoProdutividadeMedio,
        Map<String, Long> projetosPorStatus,
        List<DashboardProjetoDTO> projetos
) {
}