package br.com.fiap.inovagab.controller;

import br.com.fiap.inovagab.dto.DashboardDTO;
import br.com.fiap.inovagab.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardDTO> gerarDashboard() {
        return ResponseEntity.ok(dashboardService.gerarDashboard());
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<DashboardDTO> gerarDashboardPorProjeto(
            @PathVariable String projetoId
    ) {
        return ResponseEntity.ok(
                dashboardService.gerarDashboardPorProjeto(projetoId)
        );
    }
}