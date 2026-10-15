package br.com.fiap.inovagab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EstrategiaNaoEncontradaException extends RuntimeException {
    public EstrategiaNaoEncontradaException(String id) {
        super("Estratégia não encontrada com o id: " + id);
    }
}
