package br.ufsm.poli.csi.lauren.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;
    @NotBlank(message = "O campo genero é obrigatório")
    private String genero;
    @NotBlank(message = "O campo artista é obrigatório")
    private String artista;
    @NotNull(message = "O campo ano é obrigatório")
    private Integer ano;
    private Float notaMedia = 3F;
    @Nullable
    private String imagem;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();
}