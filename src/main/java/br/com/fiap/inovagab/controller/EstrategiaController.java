package br.com.fiap.inovagab.controller;

import br.com.fiap.inovagab.dto.EstrategiaCadastroDTO;
import br.com.fiap.inovagab.dto.EstrategiaExibicaoDTO;
import br.com.fiap.inovagab.service.EstrategiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/estrategias")
@RequiredArgsConstructor
public class EstrategiaController {

    private final EstrategiaService estrategiaService;

    /** Consulta liberada para os 3 perfis. Use ?ativas=true para ver apenas as vigentes. */
    @GetMapping
    public List<EstrategiaExibicaoDTO> listar(
            @RequestParam(name = "ativas", defaultValue = "false") boolean somenteAtivas) {
        return estrategiaService.listar(somenteAtivas);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('LIDER')")
    public EstrategiaExibicaoDTO criar(@Valid @RequestBody EstrategiaCadastroDTO dto) {
        return estrategiaService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIDER')")
    public EstrategiaExibicaoDTO atualizar(@PathVariable String id,
                                           @Valid @RequestBody EstrategiaCadastroDTO dto) {
        return estrategiaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('LIDER')")
    public void excluir(@PathVariable String id) {
        estrategiaService.excluir(id);
    }
}
