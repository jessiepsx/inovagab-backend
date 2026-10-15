package br.com.fiap.inovagab.exception;

public class ProjetoNaoEncontradoException extends RuntimeException {

    public ProjetoNaoEncontradoException() {
        super("Projeto não encontrado.");
    }
}