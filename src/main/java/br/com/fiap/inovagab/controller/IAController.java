package br.com.fiap.inovagab.controller;

import br.com.fiap.inovagab.dto.IADTO;
import br.com.fiap.inovagab.service.IAService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
public class IAController {

    private final IAService iaService;

    @PostMapping("/ideias/{ideiaId}/analisar")
    @PreAuthorize("hasRole('GESTOR')")
    public IADTO analisarIdeia(@PathVariable String ideiaId) {
        return iaService.analisarIdeia(ideiaId);
    }

    @GetMapping("/ranking")
    @PreAuthorize("hasAnyRole('GESTOR', 'LIDER')")
    public List<IADTO> ranking() {
        return iaService.ranking();
    }
}
