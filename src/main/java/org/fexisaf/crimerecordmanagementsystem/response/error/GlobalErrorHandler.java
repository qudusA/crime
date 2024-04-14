package org.fexisaf.crimerecordmanagementsystem.response.error;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.twilio.exception.ApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalErrorHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException notFoundException){
        ErrorResponse response = new ErrorResponse();
        response.setDate(LocalDateTime.now());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setStatusName(HttpStatus.NOT_FOUND.name());
        response.setMessage(notFoundException.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> fieldValidation(MethodArgumentNotValidException exception){
        List<String> errList = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getField).toList();
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("status", HttpStatus.BAD_REQUEST.value());
        errMap.put("statusName", HttpStatus.BAD_REQUEST.name());
        errMap.put("errorTime", LocalDateTime.now());
        errMap.put("message", errList);
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> emailException(MessagingException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> jwtException(JwtException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> apiException(ApiException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> apiException(RuntimeException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<?> dateTimeException(DateTimeException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( UnexpectedTypeException.class)
    public ResponseEntity<?> unexpectedException( UnexpectedTypeException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityException( DataIntegrityViolationException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( IOException.class)
    public ResponseEntity<?> ioException( IOException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( AmazonS3Exception.class)
    public ResponseEntity<?> amazonS3Exception( AmazonS3Exception e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( AuthenticationServiceException.class)
    public ResponseEntity<?> amazonS3Exception( AuthenticationServiceException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler( BadCredentialsException.class)
    public ResponseEntity<?> amazonS3Exception( BadCredentialsException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler( RuntimeException.class)
//    public ResponseEntity<?> runTimeException( RuntimeException e){
//        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler( ExpiredJwtException.class)
    public ResponseEntity<?> expiredJwtException( ExpiredJwtException e){
        ErrorResponse errorResponse = getErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private static ErrorResponse getErrorResponse(String e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e);
        errorResponse.setDate(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatusName(HttpStatus.BAD_REQUEST.name());
        return errorResponse;
    }


}
