package br.ufsm.poli.csi.lauren.controller;

import br.ufsm.poli.csi.lauren.dto.ResponseDTO;
import br.ufsm.poli.csi.lauren.infra.security.TokenServiceJWT;
import br.ufsm.poli.csi.lauren.records.DadosAutenticacao;
import br.ufsm.poli.csi.lauren.records.DadosTokenJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login", produces = {"application/json"})
@Tag(name = "Autenticação", description = "Endpoint para realizar login")
public class LoginController {

    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenServiceJWT;

    public LoginController(AuthenticationManager manager, TokenServiceJWT tokenServiceJWT) {
        this.manager = manager;
        this.tokenServiceJWT = tokenServiceJWT;
    }

    @PostMapping
    @Operation(
            summary = "Efetuar login",
            description = "Realiza a autenticação do usuário e retorna um token JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso",
                            content = @Content(schema = @Schema(example = "{ \"data\": \"token_jwt\", \"message\": \"Success\" }"))),
                    @ApiResponse(responseCode = "400", description = "Dados de login inválidos",
                            content = @Content(schema = @Schema(example = "{ \"data\": null, \"message\": \"Dados de login inválidos\" }"))),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                            content = @Content(schema = @Schema(example = "{ \"data\": null, \"message\": \"Ocorreu um erro ao processar seu login!\" }")))
            }
    )
    public ResponseDTO<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            Authentication autenticado = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            Authentication at = manager.authenticate(autenticado);

            User user = (User) at.getPrincipal();

            return ResponseDTO.ok(this.tokenServiceJWT.gerarToken(user));
        } catch (BadCredentialsException e) {
            return ResponseDTO.<DadosTokenJWT>builder().data(null).message("Dados de login inválidos").build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<DadosTokenJWT>builder().data(null).message("Ocorreu um erro ao processar seu login!").build();
        }
    }
}
