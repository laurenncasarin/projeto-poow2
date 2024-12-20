package br.ufsm.poli.csi.lauren.infra.security;


import br.ufsm.poli.csi.lauren.records.DadosTokenJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Tag(name = "Token JWT", description = "Serviço para gerar e validar tokens JWT")
public class TokenServiceJWT {
    public DadosTokenJWT gerarToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            return new DadosTokenJWT(JWT.create()
                    .withIssuer("Manchinha")
                    .withSubject(user.getUsername())
                    .withClaim("ROLE", user.getAuthorities().stream().toList().get(0).toString())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm), dataExpiracao().getEpochSecond());
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error ao gerar o token JWT", e);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            return JWT.require(algorithm)
                    .withIssuer("Manchinha")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

    public String getEmailUserRequest() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

