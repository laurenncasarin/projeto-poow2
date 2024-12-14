package br.ufsm.poli.csi.lauren.dto;

import br.ufsm.poli.csi.lauren.model.Usuario;

import java.util.List;

public record UsuarioDTO(String nome, String email, String permissao) {
    public static UsuarioDTO from(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(), usuario.getEmail(), usuario.getPermissao());
    }
    public static List<UsuarioDTO> from(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDTO::from).toList();
    }
}
