package com.egc.atmservice.constoller.handler;


import com.egc.atmservice.domain.exception.AtmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

import static com.egc.atmservice.domain.exception.ExceptionMassage.UNEXPECTED_ERROR;

@Slf4j
@ControllerAdvice
public class ControllerExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                 .filter(error -> error instanceof FieldError)
                 .map(error -> (FieldError) error)
                 .map(fieldError -> ErrorInfo.builder()
                                             .field(fieldError.getField())
                                             .message(fieldError.getDefaultMessage())
                                             .build())
                 .collect(Collectors.toList());
    }

    @ExceptionHandler(AtmException.class)
    public ResponseEntity<ErrorInfo> handleBankServiceException(AtmException ex) {
        ResponseEntity.BodyBuilder respBuilder = ResponseEntity.badRequest();
        ResponseEntity<ErrorInfo> response = respBuilder.body(ErrorInfo.builder()
                                                                       .message(ex.getMessage())
                                                                       .build());
        log.error("BankServiceException", ex);
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<List<ErrorInfo>> handleRuntimeException(RuntimeException ex) {
        ResponseEntity.BodyBuilder resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(ex.getMessage(), ex);
        ErrorInfo error = ErrorInfo.builder().message(UNEXPECTED_ERROR.name()).build();
        return resp.body(List.of(error));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<List<ErrorInfo>> handleRuntimeException(HttpClientErrorException ex) {
        ResponseEntity.BodyBuilder resp = ResponseEntity.status(ex.getStatusCode());
        log.error(ex.getMessage(), ex);
        ErrorInfo error = ErrorInfo.builder().message(ex.getStatusCode().getReasonPhrase()).build();
        return resp.body(List.of(error));
    }
}