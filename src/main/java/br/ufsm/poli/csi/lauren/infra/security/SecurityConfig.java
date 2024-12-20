package br.ufsm.poli.csi.lauren.infra.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Tag(name = "Configuração de Segurança", description = "Configuração de segurança da aplicação")
public class SecurityConfig {
    private final AutenticacaoFilter autenticacaoFilter;

    public SecurityConfig(AutenticacaoFilter filtro) {
        this.autenticacaoFilter = filtro;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                                .requestMatchers(HttpMethod.GET, "/usuarios").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/usuarios/{id}").hasAuthority("USER")
                                .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/usuarios/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/albuns").permitAll()
                                .requestMatchers(HttpMethod.GET, "/albuns/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/albuns").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/albuns/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/albuns/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/albuns/avaliar/{id_album}").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-ui.html").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(this.autenticacaoFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



