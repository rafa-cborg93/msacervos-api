package br.cborg.msacervos.exceptions;

import br.cborg.msacervos.domain.acervo.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class MsAcervosExceptionHandler {

    @ExceptionHandler(ValidateRequestException.class)
    public final ResponseEntity<Object> handleValidateRequestException(ValidateRequestException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ISBNAlReadyExistsException.class)
    public final ResponseEntity<Object> handleISBNAlReadyExistsException(ISBNAlReadyExistsException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
