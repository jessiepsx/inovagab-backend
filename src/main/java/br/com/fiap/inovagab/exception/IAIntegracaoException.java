package br.com.fiap.inovagab.exception;

public class IAIntegracaoException extends RuntimeException {
    public IAIntegracaoException(String message) {
        super(message);
    }

    public IAIntegracaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
