package br.cborg.msacervos.exceptions;

public class ValidateRequestException extends RuntimeException{
    public ValidateRequestException(String message) {
        super(message);
    }
}
