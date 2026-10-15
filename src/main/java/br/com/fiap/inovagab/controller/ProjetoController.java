package br.com.fiap.inovagab.controller;

import br.com.fiap.inovagab.dto.ProjetoCadastroDTO;
import br.com.fiap.inovagab.dto.ProjetoExibicaoDTO;
import br.com.fiap.inovagab.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<ProjetoExibicaoDTO> cadastrar(
            @RequestBody @Valid ProjetoCadastroDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projetoService.cadastrar(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GESTOR', 'LIDER')")
    public ResponseEntity<List<ProjetoExibicaoDTO>> listarTodos() {

        return ResponseEntity.ok(projetoService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTOR', 'LIDER')")
    public ResponseEntity<ProjetoExibicaoDTO> buscarPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(projetoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<ProjetoExibicaoDTO> atualizar(
            @PathVariable String id,
            @RequestBody @Valid ProjetoCadastroDTO dto) {

        return ResponseEntity.ok(
                projetoService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> excluir(
            @PathVariable String id) {

        projetoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}