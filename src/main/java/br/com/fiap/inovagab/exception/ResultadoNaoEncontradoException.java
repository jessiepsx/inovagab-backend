package br.com.fiap.inovagab.exception;

public class ResultadoNaoEncontradoException extends RuntimeException {

    public ResultadoNaoEncontradoException() {
        super("Resultado não encontrado.");
    }
}