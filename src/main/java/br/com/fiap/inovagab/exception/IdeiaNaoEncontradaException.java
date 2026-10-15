package br.com.fiap.inovagab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdeiaNaoEncontradaException extends RuntimeException {

    public IdeiaNaoEncontradaException(String message) {
        super(message);
    }
}