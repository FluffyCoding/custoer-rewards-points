package com.restapi.rewards.exception;

import com.restapi.rewards.dto.http.HttpResponse;
import com.restapi.rewards.dto.http.HttpValidationResponse;
import com.restapi.rewards.dto.http.ValidatorResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static com.restapi.rewards.config.Constants.METHOD_IS_NOT_ALLOWED;
import static com.restapi.rewards.config.Constants.VALIDATION_FAILED;
import static org.springframework.http.HttpStatus.*;

@Log4j2
@RestControllerAdvice
public class CustomExceptionHandling extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable mostSpecificCause = ex.getMostSpecificCause();

        String exceptionName = mostSpecificCause.getClass().getName();
        String message = mostSpecificCause.getMessage();
        List<ValidatorResponseMessage> validatorResponseMessages = new ArrayList<>();
        var fieldError = ex.getMostSpecificCause().getClass().getSimpleName();
        validatorResponseMessages.add(new ValidatorResponseMessage(fieldError, "Malformed JSON request"));
        var response = new HttpValidationResponse(BAD_REQUEST.value(), BAD_REQUEST, exceptionName, message, validatorResponseMessages);
        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ValidatorResponseMessage> validatorResponseMessages = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> {
                    var errorDetails = new ValidatorResponseMessage(error.getField(), error.getDefaultMessage());
                    validatorResponseMessages.add(errorDetails);
                });
        var error = new HttpValidationResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), VALIDATION_FAILED, validatorResponseMessages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        HttpMethod supportedMethod = Objects.requireNonNull(ex.getSupportedHttpMethods()).iterator().next();
        var response = createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
        return new ResponseEntity<>(response, FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResponse> illegalArgument(IllegalArgumentException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HttpResponse> typeMissMatch(MethodArgumentTypeMismatchException exception) {
        String error =
                exception.getName() + " should be of type " + exception.getRequiredType().getName();

        return createHttpResponse(BAD_REQUEST, error);
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HttpResponse> entityNotFound(EntityNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                        message), httpStatus);
    }

}
