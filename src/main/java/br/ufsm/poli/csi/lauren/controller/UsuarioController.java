package br.ufsm.poli.csi.lauren.controller;

import br.ufsm.poli.csi.lauren.dto.ResponseDTO;
import br.ufsm.poli.csi.lauren.dto.UsuarioAtualizar;
import br.ufsm.poli.csi.lauren.dto.UsuarioCriacao;
import br.ufsm.poli.csi.lauren.dto.UsuarioDTO;
import br.ufsm.poli.csi.lauren.model.Usuario;
import br.ufsm.poli.csi.lauren.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios", produces = {"application/json"})
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseDTO<List<UsuarioDTO>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseDTO.ok(UsuarioDTO.from(usuarios));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico com base no ID fornecido")
    public ResponseDTO<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(u -> ResponseDTO.ok(UsuarioDTO.from(u)))
                .orElse(ResponseDTO.notFound());
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    public ResponseDTO<UsuarioDTO> criarUsuario(@RequestBody @Valid UsuarioCriacao usuario) {
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return ResponseDTO.ok(UsuarioDTO.from(novoUsuario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário com base no ID fornecido")
    public ResponseDTO<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioAtualizar usuario) {
        return usuarioService.atualizar(id, usuario)
                .map(u -> ResponseDTO.ok(UsuarioDTO.from(u)))
                .orElse(ResponseDTO.notFound());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Deleta um usuário com base no ID fornecido")
    public ResponseDTO<Void> deletarUsuario(@PathVariable Long id) {
        if (usuarioService.deletar(id)) {
            return ResponseDTO.noContent();
        } else {
            return ResponseDTO.notFound();
        }
    }
}
