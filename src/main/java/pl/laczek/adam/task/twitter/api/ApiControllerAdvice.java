package pl.laczek.adam.task.twitter.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.laczek.adam.task.twitter.api.exception.ProfanityException;
import pl.laczek.adam.task.twitter.api.exception.UserNotExistsException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice(basePackages = {"pl.laczek.adam.task.twitter"})
public class ApiControllerAdvice {
    private static final String ERROR_FORMAT = "%s: %s";

    @ExceptionHandler(ProfanityException.class)
    public ResponseEntity<Object> profanity(ProfanityException exception) {
        log.debug(exception.getMessage(), exception);
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> argumentException(IllegalArgumentException exception) {
        log.debug(exception.getMessage(), exception);
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<Object> userNotFound(UserNotExistsException exception) {
        log.debug(exception.getMessage(), exception);
        return ResponseEntity
                .status(NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        return ResponseEntity
                .badRequest()
                .body(format(ex.getFieldError()));
    }

    private static String format(FieldError fieldError) {
        return String.format(ERROR_FORMAT, fieldError.getField(), fieldError.getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> otherExceptions(Exception exception) {
        log.debug(exception.getMessage(), exception);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
