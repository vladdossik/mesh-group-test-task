package mesh_group.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        ErrorResponse exceptionResponse = new ErrorResponse(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(NotFoundException exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<Object> handleMethodNotAllowedException(MethodNotAllowedException exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(javax.validation.ConstraintViolationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(InsufficientFundsException exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(JwtFilterException.class)
    public ResponseEntity<ErrorResponse> handleMissingAuthorizationHeader(JwtFilterException exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(InvalidInputException exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
