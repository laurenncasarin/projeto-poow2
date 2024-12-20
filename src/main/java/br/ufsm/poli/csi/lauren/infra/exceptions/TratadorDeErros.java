package br.ufsm.poli.csi.lauren.infra.exceptions;

import br.ufsm.poli.csi.lauren.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Tag(name = "Tratador de Erros", description = "Trata os erros lançados pela aplicação")
public class TratadorDeErros {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity tratarErro500(Exception e){
        return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity tratarErro401(Exception e){
        return ResponseEntity.status(401).body(e.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        List<FieldError> erros = ex.getFieldErrors();
        List<DadosErroValidacao> dados = new ArrayList<>();
        for(FieldError fe: erros){
            dados.add(new DadosErroValidacao(fe.getField(), fe.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }
    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseDTO<List<DadosErroValidacao>> tratarErro400(ConstraintViolationException ex){
        List<DadosErroValidacao> dados = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> {
            dados.add(new DadosErroValidacao(cv.getPropertyPath().toString(), cv.getMessage()));
        });
        return ResponseDTO.badRequest(dados);
    }

}
