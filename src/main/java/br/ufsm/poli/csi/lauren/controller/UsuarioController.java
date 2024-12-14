package br.ufsm.poli.csi.lauren.controller;


import br.ufsm.poli.csi.lauren.dto.ResponseDTO;
import br.ufsm.poli.csi.lauren.dto.UsuarioAtualizar;
import br.ufsm.poli.csi.lauren.dto.UsuarioCriacao;
import br.ufsm.poli.csi.lauren.dto.UsuarioDTO;
import br.ufsm.poli.csi.lauren.model.Usuario;
import br.ufsm.poli.csi.lauren.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseDTO<List<UsuarioDTO>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseDTO.ok(UsuarioDTO.from(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseDTO<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(u -> ResponseDTO.ok(UsuarioDTO.from(u)))
                .orElse(ResponseDTO.notFound());
    }

    @PostMapping
    public ResponseDTO<UsuarioDTO> criarUsuario(@RequestBody @Valid UsuarioCriacao usuario) {
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return ResponseDTO.ok(UsuarioDTO.from(novoUsuario));
    }

    @PutMapping("/{id}")
    public ResponseDTO<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioAtualizar usuario) {
        return usuarioService.atualizar(id, usuario)
                .map(u -> ResponseDTO.ok(UsuarioDTO.from(u)))
                .orElse(ResponseDTO.notFound());
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deletarUsuario(@PathVariable Long id) {
        if (usuarioService.deletar(id)) {
            return ResponseDTO.noContent();
        } else {
            return ResponseDTO.notFound();
        }
    }
}
