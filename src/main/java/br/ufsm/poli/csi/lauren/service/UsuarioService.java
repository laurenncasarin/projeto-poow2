package br.ufsm.poli.csi.lauren.service;

import br.ufsm.poli.csi.lauren.dto.UsuarioAtualizar;
import br.ufsm.poli.csi.lauren.dto.UsuarioCriacao;
import br.ufsm.poli.csi.lauren.infra.security.TokenServiceJWT;
import br.ufsm.poli.csi.lauren.model.Usuario;
import br.ufsm.poli.csi.lauren.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final TokenServiceJWT tokenServiceJWT;

    public UsuarioService(UsuarioRepository usuarioRepository, TokenServiceJWT tokenServiceJWT) {
        this.usuarioRepository = usuarioRepository;
        this.tokenServiceJWT = tokenServiceJWT;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario salvar(UsuarioCriacao usuario) {
        return usuarioRepository.save(Usuario.from(usuario));
    }

    public Optional<Usuario> atualizar(Long id, UsuarioAtualizar usuario) {
        Optional<Usuario> usuarioSalvo = usuarioRepository.findById(id);
        if (usuarioSalvo.isPresent() && usuarioSalvo.get().getEmail().equals(tokenServiceJWT.getEmailUserRequest())) {
            usuarioSalvo.get().setNome(usuario.getNome());
            usuarioSalvo.get().setEmail(usuario.getEmail());
            return Optional.of(usuarioRepository.save(usuarioSalvo.get()));
        } else {
            return Optional.empty();
        }
    }

    public boolean deletar(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return true;
        } else {
            return false;
        }
    }
}
