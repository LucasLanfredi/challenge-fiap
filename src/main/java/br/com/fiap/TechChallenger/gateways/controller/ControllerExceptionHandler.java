package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.domains.dto.ErroCustomizado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroCustomizado> erroValidacao(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErroCustomizado erro = new ErroCustomizado(
                Instant.now(),
                status.value(),
                "Dados inválidos",
                request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()){
            erro.addErroValidacao(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(erro);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class )
    public ResponseEntity<ErroCustomizado> handleJdbcConstraintViolation(
            SQLIntegrityConstraintViolationException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErroCustomizado erro = new ErroCustomizado(
                Instant.now(),
                status.value(),
                "Violação de integridade",
                request.getRequestURI()
        );

        if (ex.getMessage().contains("EMAIL")) {
            erro.addErroValidacao("email", "Já existe um usuário cadastrado com este e-mail.");
        } else if (ex.getMessage().contains("LOGIN")) {
            erro.addErroValidacao("login", "Já existe um usuário cadastrado com este login.");
        } else {
            erro.addErroValidacao("dados", "Violação de restrição de integridade.");
        }

        return ResponseEntity.status(status).body(erro);
    }
 
}
