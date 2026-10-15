package br.com.fiap.inovagab.controller;

import br.com.fiap.inovagab.dto.AuditoriaExibicaoDTO;
import br.com.fiap.inovagab.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auditorias")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    @PreAuthorize("hasRole('LIDER')")
    public List<AuditoriaExibicaoDTO> listarRecentes() {
        return auditoriaService.listarRecentes();
    }
}
