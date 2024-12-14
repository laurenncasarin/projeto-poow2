package br.ufsm.poli.csi.lauren.service;

import br.ufsm.poli.csi.lauren.model.Usuario;
import br.ufsm.poli.csi.lauren.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AutenticacaoService implements UserDetailsService {
    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = this.repository.findByEmail(login);
        if (Objects.isNull(usuario)) {
            throw new UsernameNotFoundException("Usuário ou senha incorretos");
        } else {
            UserDetails user = User.withUsername(usuario.getEmail()).password(usuario.getSenha())
                    .authorities(usuario.getPermissao()).build();
            return user;
        }
    }
}
