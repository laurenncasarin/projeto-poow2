package br.ufsm.poli.csi.lauren.model;
import br.ufsm.poli.csi.lauren.dto.UsuarioCriacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @JsonIgnore
    private String senha;

    private String permissao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;

    public static Usuario from(UsuarioCriacao usuarioCriacao) {
        return Usuario.builder()
                .nome(usuarioCriacao.getNome())
                .email(usuarioCriacao.getEmail())
                .senha(new BCryptPasswordEncoder().encode(usuarioCriacao.getSenha()))
                .permissao("USER")
                .build();
    }
}
