package br.ufsm.poli.csi.lauren.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String genero;
    private String artista;
    private String musicas;
    private Integer ano;
    private Float notaMedia;
    private String imagem;

    @OneToMany(mappedBy = "album")
    private List<Avaliacao> avaliacoes;
}

