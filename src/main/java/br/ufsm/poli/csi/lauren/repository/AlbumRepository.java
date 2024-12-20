package br.ufsm.poli.csi.lauren.repository;

import br.ufsm.poli.csi.lauren.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
