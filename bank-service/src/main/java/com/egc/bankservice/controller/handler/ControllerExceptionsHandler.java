//package com.egc.bankservice.controller.handler;
//
//
//import com.egc.bankservice.model.exception.BankServiceException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.orm.ObjectOptimisticLockingFailureException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.egc.bankservice.model.exception.ExceptionMassage.OPTIMISTIC_ENTITY_HAS_BEEN_MODIFIED;
//import static com.egc.bankservice.model.exception.ExceptionMassage.UNEXPECTED_ERROR;
//
//@Slf4j
//@ControllerAdvice
//public class ControllerExceptionsHandler {
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public List<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        return ex.getBindingResult().getAllErrors().stream()
//                 .filter(error -> error instanceof FieldError)
//                 .map(error -> (FieldError) error)
//                 .map(fieldError -> ErrorInfo.builder()
//                                             .field(fieldError.getField())
//                                             .message(fieldError.getDefaultMessage())
//                                             .build())
//                 .collect(Collectors.toList());
//    }
//
//    @ExceptionHandler(BankServiceException.class)
//    public ResponseEntity<ErrorInfo> handleBankServiceException(BankServiceException ex) {
//        ResponseEntity.BodyBuilder respBuilder = getResponseEntityBuilder(ex);
//        ResponseEntity<ErrorInfo> response = respBuilder.body(ErrorInfo.builder()
//                                                                       .message(ex.getMessage())
//                                                                       .build());
//        log.error("BankServiceException", ex);
//        return response;
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ErrorInfo> handleBankServiceException(AuthenticationException ex) {
//        ResponseEntity<ErrorInfo> response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                                                           .body(ErrorInfo.builder()
//                                                                          .message(ex.getMessage())
//                                                                          .build());
//        log.error("AuthenticationException", ex);
//        return response;
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<List<ErrorInfo>> handleRuntimeException(RuntimeException ex) {
//        ResponseEntity.BodyBuilder resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
//        log.error(ex.getMessage(), ex);
//        ErrorInfo error = ErrorInfo.builder().message(UNEXPECTED_ERROR.name()).build();
//        return resp.body(List.of(error));
//    }
//
//    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
//    public ResponseEntity<List<ErrorInfo>> handleSpreadSheetException(ObjectOptimisticLockingFailureException ex) {
//        ResponseEntity.BodyBuilder resp = ResponseEntity.badRequest();
//        ErrorInfo error = ErrorInfo.builder().message(OPTIMISTIC_ENTITY_HAS_BEEN_MODIFIED.name()).build();
//        return resp.body(List.of(error));
//    }
//
//
//    private ResponseEntity.BodyBuilder getResponseEntityBuilder(BankServiceException ex) {
//        switch (ex.getExceptionCode()) {
//            case BAD_REQUEST:
//                return ResponseEntity.badRequest();
//            case UNAUTHORIZED:
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
//            case FORBIDDEN:
//                return ResponseEntity.status(HttpStatus.FORBIDDEN);
//            case NOT_FOUND:
//                return ResponseEntity.status(HttpStatus.NOT_FOUND);
//            default:
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}