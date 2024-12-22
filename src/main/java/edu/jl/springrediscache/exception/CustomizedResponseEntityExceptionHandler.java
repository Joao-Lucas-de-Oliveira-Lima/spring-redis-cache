package edu.jl.springrediscache.exception;

import edu.jl.springrediscache.dto.excepiton.ExceptionDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handlerException(WebRequest webRequest, Exception exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildExceptionDTO(webRequest, exception));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handlerResourceNotFoundException(WebRequest webRequest, Exception exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildExceptionDTO(webRequest, exception));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDTO> handlerBadRequestException(WebRequest webRequest, Exception exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionDTO(webRequest, exception));
    }

    private ExceptionDTO buildExceptionDTO(WebRequest webRequest, Exception exception){
        return new ExceptionDTO(new Date(), exception.getMessage(), webRequest.getDescription(false));
    }
}
