package br.ufsm.poli.csi.lauren;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Trabalho POOWII API",
                version = "1.0",
                description = "API para gerenciar usuários e autenticação no Trabalho POOWII",
                contact = @Contact(
                        name = "Lauren Nicoloso Casarin",
                        email = "lauren.nicoloso@acad.ufsm.br"
                )
        )
)
@SpringBootApplication
public class SistemaAlbunsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaAlbunsApplication.class, args);
    }

}
