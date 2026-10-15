package br.com.fiap.inovagab.controller;

import br.com.fiap.inovagab.dto.IdeiaCadastroDTO;
import br.com.fiap.inovagab.dto.IdeiaExibicaoDTO;
import br.com.fiap.inovagab.dto.IdeiaStatusUpdateDTO;
import br.com.fiap.inovagab.model.StatusIdeia;
import br.com.fiap.inovagab.service.IdeiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ideias")
@RequiredArgsConstructor
public class IdeiaController {

    private final IdeiaService ideiaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('OPERADOR')")
    public IdeiaExibicaoDTO criar(@Valid @RequestBody IdeiaCadastroDTO dto) {
        return ideiaService.criar(dto);
    }

    /** GESTOR e LIDER veem todas as ideias; use ?status=PENDENTE para a fila de triagem. */
    @GetMapping
    @PreAuthorize("hasAnyRole('GESTOR', 'LIDER')")
    public List<IdeiaExibicaoDTO> listarTodas(@RequestParam(required = false) StatusIdeia status) {
        return ideiaService.listarTodas(status);
    }

    /** OPERADOR acompanha somente as próprias ideias enviadas. */
    @GetMapping("/minhas")
    @PreAuthorize("hasRole('OPERADOR')")
    public List<IdeiaExibicaoDTO> listarMinhas() {
        return ideiaService.listarMinhas();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OPERADOR')")
    public IdeiaExibicaoDTO atualizar(@PathVariable String id,
                                      @Valid @RequestBody IdeiaCadastroDTO dto) {
        return ideiaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('OPERADOR')")
    public void excluir(@PathVariable String id) {
        ideiaService.excluir(id);
    }

    /** GESTOR aprova, recusa ou move a ideia para "em projeto". */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('GESTOR')")
    public IdeiaExibicaoDTO atualizarStatus(@PathVariable String id,
                                            @Valid @RequestBody IdeiaStatusUpdateDTO dto) {
        return ideiaService.atualizarStatus(id, dto.status());
    }
}
