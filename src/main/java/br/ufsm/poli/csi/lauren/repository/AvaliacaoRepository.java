package br.ufsm.poli.csi.lauren.repository;

import br.ufsm.poli.csi.lauren.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Boolean existsAvaliacaoByUsuario_IdAndAlbum_Id(Integer idUsuario, Integer idAlbum);

    List<Avaliacao> findAllByAlbum_Id(Integer id);
}
