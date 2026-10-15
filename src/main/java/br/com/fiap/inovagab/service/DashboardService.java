package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.dto.DashboardDTO;
import br.com.fiap.inovagab.dto.DashboardProjetoDTO;
import br.com.fiap.inovagab.model.Projeto;
import br.com.fiap.inovagab.model.Resultado;
import br.com.fiap.inovagab.repository.ProjetoRepository;
import br.com.fiap.inovagab.repository.ResultadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ProjetoRepository projetoRepository;
    private final ResultadoRepository resultadoRepository;

    public DashboardService(
            ProjetoRepository projetoRepository,
            ResultadoRepository resultadoRepository
    ) {
        this.projetoRepository = projetoRepository;
        this.resultadoRepository = resultadoRepository;
    }

    public DashboardDTO gerarDashboard() {

        List<Projeto> projetos = projetoRepository.findAll();
        List<Resultado> resultados = resultadoRepository.findAll();

        long quantidadeProjetos = projetos.size();

        double investimentoTotal = resultados.stream()
                .mapToDouble(Resultado::getInvestimento)
                .sum();

        double retornoFinanceiroTotal = resultados.stream()
                .mapToDouble(Resultado::getRetornoFinanceiro)
                .sum();

        double lucroTotal = resultados.stream()
                .mapToDouble(Resultado::getLucro)
                .sum();

        double roiGeral = 0.0;

        if (investimentoTotal != 0) {
            roiGeral = ((retornoFinanceiroTotal - investimentoTotal)
                    / investimentoTotal) * 100;
        }

        double aumentoProdutividadeMedio = resultados.stream()
                .mapToDouble(Resultado::getAumentoProdutividade)
                .average()
                .orElse(0.0);

        Map<String, Long> projetosPorStatus = projetos.stream()
                .collect(Collectors.groupingBy(
                        Projeto::getStatus,
                        Collectors.counting()
                ));

        List<DashboardProjetoDTO> projetosDashboard = projetos.stream()
                .map(projeto -> {

                    List<Resultado> resultadosProjeto =
                            resultados.stream()
                                    .filter(resultado -> resultado.getProjetoId().equals(projeto.getId()))
                                    .toList();

                    double investimento = resultadosProjeto.stream()
                            .mapToDouble(Resultado::getInvestimento)
                            .sum();

                    double retornoFinanceiro = resultadosProjeto.stream()
                            .mapToDouble(Resultado::getRetornoFinanceiro)
                            .sum();

                    double roi = 0.0;

                    if (investimento != 0) {
                        roi = ((retornoFinanceiro - investimento) / investimento) * 100;
                    }

                    double aumentoProdutividade = resultadosProjeto.stream()
                            .mapToDouble(Resultado::getAumentoProdutividade)
                            .average()
                            .orElse(0.0);

                    return new DashboardProjetoDTO(
                            projeto.getId(),
                            projeto.getNome(),
                            projeto.getDescricao(),
                            projeto.getEstrategiaId(),
                            projeto.getEtapa(),
                            projeto.getStatus(),
                            investimento,
                            retornoFinanceiro,
                            projeto.getProgresso(),
                            projeto.getPrazo(),
                            roi,
                            aumentoProdutividade
                    );
                })
                .toList();

        return new DashboardDTO(
                quantidadeProjetos,
                investimentoTotal,
                retornoFinanceiroTotal,
                lucroTotal,
                roiGeral,
                aumentoProdutividadeMedio,
                projetosPorStatus,
                projetosDashboard
        );
    }

    public DashboardDTO gerarDashboardPorProjeto(String projetoId) {

        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado."));

        List<Resultado> resultados = resultadoRepository.findByProjetoId(projetoId);

        double investimentoTotal = resultados.stream()
                .mapToDouble(Resultado::getInvestimento)
                .sum();

        double retornoFinanceiroTotal = resultados.stream()
                .mapToDouble(Resultado::getRetornoFinanceiro)
                .sum();

        double lucroTotal = resultados.stream()
                .mapToDouble(Resultado::getLucro)
                .sum();

        double roiGeral = 0.0;

        if (investimentoTotal != 0) {
            roiGeral = ((retornoFinanceiroTotal - investimentoTotal)
                    / investimentoTotal) * 100;
        }

        double aumentoProdutividadeMedio = resultados.stream()
                .mapToDouble(Resultado::getAumentoProdutividade)
                .average()
                .orElse(0.0);

        Map<String, Long> projetosPorStatus = Map.of(
                projeto.getStatus(), 1L
        );

        List<DashboardProjetoDTO> projetosDashboard = List.of(
                new DashboardProjetoDTO(
                        projeto.getId(),
                        projeto.getNome(),
                        projeto.getDescricao(),
                        projeto.getEstrategiaId(),
                        projeto.getEtapa(),
                        projeto.getStatus(),
                        investimentoTotal,
                        retornoFinanceiroTotal,
                        projeto.getProgresso(),
                        projeto.getPrazo(),
                        roiGeral,
                        aumentoProdutividadeMedio
                )
        );

        return new DashboardDTO(
                1L,
                investimentoTotal,
                retornoFinanceiroTotal,
                lucroTotal,
                roiGeral,
                aumentoProdutividadeMedio,
                projetosPorStatus,
                projetosDashboard
        );
    }
}