package br.ufsm.poli.csi.lauren.dto;

import br.ufsm.poli.csi.lauren.model.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {

    private Long id;
    private String titulo;
    private String artista;
    private int anoLancamento;
    private float notaMedia;


    public static AlbumDTO from(Album album) {
        return new AlbumDTO(album.getId().longValue(), album.getNome(), album.getArtista(), album.getAno(), album.getNotaMedia());
    }
}
