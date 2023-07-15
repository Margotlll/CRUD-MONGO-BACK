package com.tutorial.crudmongoback.global.exceptions;

import com.tutorial.crudmongoback.global.dto.MessageDto;
import com.tutorial.crudmongoback.global.utils.Operations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptions {

    //exception de validacion de campos, para enviar mensaje cuando no se encuantra el elemento que se quiere obtener o modificar.
    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public ResponseEntity<MessageDto> trowNotFoundException(ResourceNotFoundExceptions e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(HttpStatus.NOT_FOUND,e.getMessage()));
    }

    //exception de validacion de campo, para no tener nombres duplicados
    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<MessageDto> trowAttributeException(AttributeException e){
        return ResponseEntity.badRequest().body(new MessageDto(HttpStatus.BAD_REQUEST,e.getMessage()));
    }

    //excepciones directas a la clase exception, excpciones de validación de campos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> generalException(Exception e){
        return ResponseEntity.internalServerError().body(new MessageDto(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()));
    }

    //hacer que los mensajes de validacion del nombre y precio me salgan en un array
    //para hacer que los mensajes en Lista me salgan sin corchetes creamos la clase operation.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDto> validationException(MethodArgumentNotValidException e){
        List<String> message=new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((err) ->{
            message.add(err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new MessageDto(HttpStatus.BAD_REQUEST, Operations.trimBrackets(message.toString())));

    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageDto> badCredentialException(BadCredentialsException e){
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(HttpStatus.NOT_FOUND, "Bad credentialsss"));

    }



}
