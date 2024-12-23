package com.simulacion.banco.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice //para interceptar las excepciones
@RestController //
public class ResponseExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)//Interceptar la excepcion
    public ResponseEntity <ExceptionResponse> modelNotFoundException(ModelNotFoundException ex, WebRequest request){ //WebRequest llegan los parametros (headers, body)
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);// implementando una funcionalidad
    }

    @ExceptionHandler(Exception.class)//Interceptar la excepcion
    public ResponseEntity <ExceptionResponse> exception(Exception ex, WebRequest request){ //WebRequest llegan los parametros (headers, body)
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)//Interceptar la excepcion
    public ResponseEntity <ExceptionResponse> modelNotFoundException(MethodArgumentNotValidException ex, WebRequest request){ //WebRequest llegan los parametros (headers, body)
        String mensaje = ex.getBindingResult().getAllErrors().stream().map(e ->
                e.getDefaultMessage()
        ).collect(Collectors.joining(", "));
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), mensaje, request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);// implementando una funcionalidad
    }// PROFUNDIZAR
}
