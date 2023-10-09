package br.cborg.msacervos.Exceptions;

public class ValidateRequestException extends RuntimeException{
    public ValidateRequestException(String message) {
        super(message);
    }
}
