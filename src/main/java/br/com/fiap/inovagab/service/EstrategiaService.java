package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.dto.EstrategiaCadastroDTO;
import br.com.fiap.inovagab.dto.EstrategiaExibicaoDTO;
import br.com.fiap.inovagab.exception.EstrategiaNaoEncontradaException;
import br.com.fiap.inovagab.model.Estrategia;
import br.com.fiap.inovagab.repository.EstrategiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstrategiaService {

    private final EstrategiaRepository estrategiaRepository;

    public List<EstrategiaExibicaoDTO> listar(boolean somenteAtivas) {
        List<Estrategia> estrategias = somenteAtivas
                ? estrategiaRepository.findByAtivaTrue()
                : estrategiaRepository.findAllByOrderByDataDesc();
        return estrategias.stream().map(EstrategiaExibicaoDTO::fromEntity).toList();
    }

    /** Usado internamente (ex.: por {@code IdeiaService}) para validar o vínculo com a estratégia vigente. */
    public Estrategia buscarEntidade(String id) {
        return estrategiaRepository.findById(id)
                .orElseThrow(() -> new EstrategiaNaoEncontradaException(id));
    }

    public EstrategiaExibicaoDTO criar(EstrategiaCadastroDTO dto) {
        Estrategia estrategia = Estrategia.builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .categoria(dto.categoria())
                .campanha(dto.campanha())
                .data(LocalDate.now())
                .ativa(dto.ativa() == null || dto.ativa())
                .build();
        return EstrategiaExibicaoDTO.fromEntity(estrategiaRepository.save(estrategia));
    }

    /** Edita a estratégia; se {@code ativa} vier no corpo, também (des)ativa a vigência. */
    public EstrategiaExibicaoDTO atualizar(String id, EstrategiaCadastroDTO dto) {
        Estrategia estrategia = buscarEntidade(id);
        estrategia.setTitulo(dto.titulo());
        estrategia.setDescricao(dto.descricao());
        estrategia.setCategoria(dto.categoria());
        estrategia.setCampanha(dto.campanha());
        if (dto.ativa() != null) {
            estrategia.setAtiva(dto.ativa());
        }
        return EstrategiaExibicaoDTO.fromEntity(estrategiaRepository.save(estrategia));
    }

    public void excluir(String id) {
        Estrategia estrategia = buscarEntidade(id);
        estrategiaRepository.delete(estrategia);
    }
}
