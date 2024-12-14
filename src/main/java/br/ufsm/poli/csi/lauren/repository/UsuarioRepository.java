package br.ufsm.poli.csi.lauren.repository;

import br.ufsm.poli.csi.lauren.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
