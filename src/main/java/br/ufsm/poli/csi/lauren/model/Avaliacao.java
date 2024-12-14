package br.ufsm.poli.csi.lauren.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_avaliacao;

    private Long id_usuario;

    private Float nota;

    @ManyToOne(fetch = FetchType.LAZY)
    private Album album;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario; // Referência ao usuário
}