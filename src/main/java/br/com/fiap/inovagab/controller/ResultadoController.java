package br.com.fiap.inovagab.controller;

import br.com.fiap.inovagab.dto.ResultadoCadastroDTO;
import br.com.fiap.inovagab.dto.ResultadoExibicaoDTO;
import br.com.fiap.inovagab.service.ResultadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoController {

    private final ResultadoService resultadoService;

    public ResultadoController(ResultadoService resultadoService) {
        this.resultadoService = resultadoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<ResultadoExibicaoDTO> cadastrar(
            @RequestBody @Valid ResultadoCadastroDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultadoService.cadastrar(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GESTOR', 'LIDER')")
    public ResponseEntity<List<ResultadoExibicaoDTO>> listarTodos() {

        return ResponseEntity.ok(resultadoService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTOR', 'LIDER')")
    public ResponseEntity<ResultadoExibicaoDTO> buscarPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(resultadoService.buscarPorId(id));
    }

    @GetMapping("/projeto/{projetoId}")
    @PreAuthorize("hasAnyRole('GESTOR', 'LIDER')")
    public ResponseEntity<List<ResultadoExibicaoDTO>> listarPorProjeto(
            @PathVariable String projetoId) {

        return ResponseEntity.ok(
                resultadoService.listarPorProjeto(projetoId)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<ResultadoExibicaoDTO> atualizar(
            @PathVariable String id,
            @RequestBody @Valid ResultadoCadastroDTO dto) {

        return ResponseEntity.ok(
                resultadoService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> excluir(
            @PathVariable String id) {

        resultadoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}