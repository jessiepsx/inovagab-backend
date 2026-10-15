package br.com.fiap.inovagab.service;

import br.com.fiap.inovagab.dto.ResultadoCadastroDTO;
import br.com.fiap.inovagab.dto.ResultadoExibicaoDTO;
import br.com.fiap.inovagab.exception.ProjetoNaoEncontradoException;
import br.com.fiap.inovagab.exception.ResultadoNaoEncontradoException;
import br.com.fiap.inovagab.model.Resultado;
import br.com.fiap.inovagab.repository.ProjetoRepository;
import br.com.fiap.inovagab.repository.ResultadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultadoService {

    private final ResultadoRepository resultadoRepository;
    private final ProjetoRepository projetoRepository;

    public ResultadoService(ResultadoRepository resultadoRepository,
                            ProjetoRepository projetoRepository) {
        this.resultadoRepository = resultadoRepository;
        this.projetoRepository = projetoRepository;
    }

    // Cadastrar resultado
    public ResultadoExibicaoDTO cadastrar(ResultadoCadastroDTO dto) {

        // Verifica se o projeto existe
        projetoRepository.findById(dto.projetoId())
                .orElseThrow(ProjetoNaoEncontradoException::new);

        Resultado resultado = new Resultado();

        resultado.setProjetoId(dto.projetoId());
        resultado.setData(dto.data());
        resultado.setDescricao(dto.descricao());
        resultado.setInvestimento(dto.investimento());
        resultado.setRetornoFinanceiro(dto.retornoFinanceiro());

        // Calcula o lucro
        double lucro = dto.retornoFinanceiro() - dto.investimento();
        resultado.setLucro(lucro);

        // Calcula o ROI
        double roi = 0.0;

        if (dto.investimento() != 0) {
            roi = ((dto.retornoFinanceiro() - dto.investimento())
                    / dto.investimento()) * 100;
        }

        resultado.setRoi(roi);

        resultado.setAumentoProdutividade(
                dto.aumentoProdutividade() != null
                        ? dto.aumentoProdutividade()
                        : 0.0
        );

        Resultado resultadoSalvo = resultadoRepository.save(resultado);

        return new ResultadoExibicaoDTO(resultadoSalvo);
    }

    // Listar todos os resultados
    public List<ResultadoExibicaoDTO> listarTodos() {

        return resultadoRepository.findAll()
                .stream()
                .map(ResultadoExibicaoDTO::new)
                .toList();
    }

    // Buscar resultado por ID
    public ResultadoExibicaoDTO buscarPorId(String id) {

        Resultado resultado = resultadoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Resultado não encontrado.")
                );

        return new ResultadoExibicaoDTO(resultado);
    }

    // Listar resultados de um projeto
    public List<ResultadoExibicaoDTO> listarPorProjeto(String projetoId) {

        projetoRepository.findById(projetoId)
                .orElseThrow(ProjetoNaoEncontradoException::new);

        return resultadoRepository.findByProjetoId(projetoId)
                .stream()
                .map(ResultadoExibicaoDTO::new)
                .toList();
    }

    // Atualizar resultado
    public ResultadoExibicaoDTO atualizar(
            String id,
            ResultadoCadastroDTO dto) {

        Resultado resultado = resultadoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Resultado não encontrado.")
                );

        projetoRepository.findById(dto.projetoId())
                .orElseThrow(ProjetoNaoEncontradoException::new);

        resultado.setProjetoId(dto.projetoId());
        resultado.setData(dto.data());
        resultado.setDescricao(dto.descricao());
        resultado.setInvestimento(dto.investimento());
        resultado.setRetornoFinanceiro(dto.retornoFinanceiro());

        double lucro = dto.retornoFinanceiro() - dto.investimento();
        resultado.setLucro(lucro);

        double roi = 0.0;

        if (dto.investimento() != 0) {
            roi = ((dto.retornoFinanceiro() - dto.investimento())
                    / dto.investimento()) * 100;
        }

        resultado.setRoi(roi);

        resultado.setAumentoProdutividade(
                dto.aumentoProdutividade() != null
                        ? dto.aumentoProdutividade()
                        : 0.0
        );

        Resultado resultadoAtualizado =
                resultadoRepository.save(resultado);

        return new ResultadoExibicaoDTO(resultadoAtualizado);
    }

    // Excluir resultado
    public void excluir(String id) {

        if (!resultadoRepository.existsById(id)) {
            throw new ResultadoNaoEncontradoException();
        }

        resultadoRepository.deleteById(id);
    }
}