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

import java.time.Instant;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handlerException(WebRequest webRequest){
        String errorMessage  = "An unexpected error occurred. Please try again later or " +
                "contact support if the problem persists.";

        ExceptionDTO exceptionDTO = new ExceptionDTO(
                Instant.now(),
                errorMessage,
                webRequest.getDescription(false)
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDTO);
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
        return new ExceptionDTO(Instant.now(), exception.getMessage(), webRequest.getDescription(false));
    }
}
