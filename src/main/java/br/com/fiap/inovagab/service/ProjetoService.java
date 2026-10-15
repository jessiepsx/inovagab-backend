package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.dto.ProjetoCadastroDTO;
import br.com.fiap.inovagab.dto.ProjetoExibicaoDTO;
import br.com.fiap.inovagab.exception.ProjetoNaoEncontradoException;
import br.com.fiap.inovagab.model.Projeto;
import br.com.fiap.inovagab.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    // Cadastrar projeto
    public ProjetoExibicaoDTO cadastrar(ProjetoCadastroDTO dto) {

        Projeto projeto = new Projeto();

        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setEstrategiaId(dto.estrategiaId());
        projeto.setEtapa(dto.etapa());
        projeto.setStatus(dto.status());
        projeto.setInvestimento(dto.investimento());
        projeto.setPrazo(dto.prazo());

        projeto.setRetornoFinanceiro(
                dto.retornoFinanceiro() != null
                        ? dto.retornoFinanceiro()
                        : 0.0
        );

        projeto.setProgresso(
                dto.progresso() != null
                        ? dto.progresso()
                        : 0.0
        );

        Projeto projetoSalvo = projetoRepository.save(projeto);

        return new ProjetoExibicaoDTO(projetoSalvo);
    }

    // Listar todos os projetos
    public List<ProjetoExibicaoDTO> listarTodos() {

        return projetoRepository.findAll()
                .stream()
                .map(ProjetoExibicaoDTO::new)
                .toList();
    }

    // Buscar projeto por ID
    public ProjetoExibicaoDTO buscarPorId(String id) {

        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(ProjetoNaoEncontradoException::new);

        return new ProjetoExibicaoDTO(projeto);
    }

    // Atualizar projeto
    public ProjetoExibicaoDTO atualizar(String id, ProjetoCadastroDTO dto) {

        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(ProjetoNaoEncontradoException::new);

        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setEstrategiaId(dto.estrategiaId());
        projeto.setEtapa(dto.etapa());
        projeto.setStatus(dto.status());
        projeto.setInvestimento(dto.investimento());
        projeto.setPrazo(dto.prazo());

        if (dto.retornoFinanceiro() != null) {
            projeto.setRetornoFinanceiro(dto.retornoFinanceiro());
        }

        if (dto.progresso() != null) {
            projeto.setProgresso(dto.progresso());
        }

        Projeto projetoAtualizado = projetoRepository.save(projeto);

        return new ProjetoExibicaoDTO(projetoAtualizado);
    }

    // Excluir projeto
    public void excluir(String id) {

        if (!projetoRepository.existsById(id)) {
            throw new ProjetoNaoEncontradoException();
        }

        projetoRepository.deleteById(id);
    }
}