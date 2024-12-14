package br.ufsm.poli.csi.lauren.controller;

import br.ufsm.poli.csi.lauren.dto.ResponseDTO;
import br.ufsm.poli.csi.lauren.infra.security.TokenServiceJWT;
import br.ufsm.poli.csi.lauren.records.DadosAutenticacao;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenServiceJWT;
    public LoginController(AuthenticationManager manager, TokenServiceJWT tokenServiceJWT){
        this.manager = manager;
        this.tokenServiceJWT = tokenServiceJWT;
    }
    @PostMapping
    public ResponseDTO efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        try{
            Authentication autenticado = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            Authentication at = manager.authenticate(autenticado);

            User user = (User) at.getPrincipal();

            return ResponseDTO.ok(this.tokenServiceJWT.gerarToken(user));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.builder().data(e.getMessage()).message("Ocorreu um erro ao processar seu login!").build();
        }
    }

}

