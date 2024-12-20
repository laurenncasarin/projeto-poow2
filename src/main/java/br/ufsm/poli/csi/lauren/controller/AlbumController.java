package br.ufsm.poli.csi.lauren.controller;

import br.ufsm.poli.csi.lauren.dto.AlbumDTO;
import br.ufsm.poli.csi.lauren.model.Album;
import br.ufsm.poli.csi.lauren.service.AlbumService;
import br.ufsm.poli.csi.lauren.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/albuns", produces = {"application/json"})
@Tag(name = "Álbuns", description = "Endpoints para gerenciamento de álbuns")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os álbuns", description = "Retorna uma lista com todos os álbuns cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de álbuns retornada com sucesso")
    public ResponseDTO<List<AlbumDTO>> listarTodos() {
        List<AlbumDTO> albuns = albumService.listarTodos();
        return ResponseDTO.ok(albuns);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar álbum por ID", description = "Retorna um álbum específico com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Álbum encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado")
    })
    public ResponseDTO<AlbumDTO> buscarPorId(@PathVariable Long id) {
        return albumService.buscarPorId(id)
                .map(ResponseDTO::ok)
                .orElse(ResponseDTO.notFound());
    }

    @PostMapping
    @Operation(summary = "Criar um novo álbum", description = "Cria um novo álbum com os dados fornecidos")
    @ApiResponse(responseCode = "200", description = "Álbum criado com sucesso")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseDTO<AlbumDTO> criarAlbum(@RequestBody Album album) {
        AlbumDTO novoAlbum = albumService.salvar(album);
        return ResponseDTO.ok(novoAlbum);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar álbum", description = "Atualiza os dados de um álbum com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Álbum atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseDTO<AlbumDTO> atualizarAlbum(@PathVariable Long id, @RequestBody Album album) {
        return albumService.atualizar(id, album)
                .map(ResponseDTO::ok)
                .orElse(ResponseDTO.notFound());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar álbum", description = "Deleta um álbum com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Álbum deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseDTO<Void> deletarAlbum(@PathVariable Long id) {
        if (albumService.deletar(id)) {
            return ResponseDTO.noContent();
        } else {
            return ResponseDTO.notFound();
        }
    }

    @PostMapping("/avaliar/{id_album}")
    @Operation(summary = "Avaliar álbum", description = "Avalia um álbum com base no ID fornecido")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseDTO<AlbumDTO> avaliarAlbum(@PathVariable Long id_album,
                                              @RequestParam @Min(value = 1) @Max(value = 5) Float nota,
                                              @RequestParam String comentario) {
        AlbumDTO album = albumService.avaliar(id_album, nota, comentario);
        if (album == null) {
            return ResponseDTO.badRequest(null);
        }
        return ResponseDTO.ok(album);
    }
}
