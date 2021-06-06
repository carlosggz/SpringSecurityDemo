package es.security.example.springsecuritydemo.application.handlers;

import es.security.example.springsecuritydemo.domain.exceptions.BookNotFoundException;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = InvalidArgumentsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResult invalidArgumentsException(InvalidArgumentsException ex) {
        return ApiErrorResult.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .description(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResult bookNotFoundException(BookNotFoundException ex) {
        return ApiErrorResult.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .description(ex.getMessage())
                .build();
    }
}
