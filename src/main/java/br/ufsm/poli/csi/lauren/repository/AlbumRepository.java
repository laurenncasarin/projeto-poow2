package br.ufsm.poli.csi.lauren.repository;

import br.ufsm.poli.csi.lauren.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
