package br.ufsm.poli.csi.lauren.service;

import br.ufsm.poli.csi.lauren.dto.AlbumDTO;
import br.ufsm.poli.csi.lauren.infra.security.TokenServiceJWT;
import br.ufsm.poli.csi.lauren.model.Album;
import br.ufsm.poli.csi.lauren.model.Avaliacao;
import br.ufsm.poli.csi.lauren.model.Usuario;
import br.ufsm.poli.csi.lauren.repository.AlbumRepository;
import br.ufsm.poli.csi.lauren.repository.AvaliacaoRepository;
import br.ufsm.poli.csi.lauren.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final TokenServiceJWT tokenServiceJWT;
    private final UsuarioRepository usuarioRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public AlbumService(AlbumRepository albumRepository, TokenServiceJWT tokenServiceJWT, UsuarioRepository usuarioRepository, AvaliacaoRepository avaliacaoRepository) {
        this.albumRepository = albumRepository;
        this.tokenServiceJWT = tokenServiceJWT;
        this.usuarioRepository = usuarioRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    // Listar todos os álbuns
    public List<AlbumDTO> listarTodos() {
        return albumRepository.findAll()
                .stream()
                .map(AlbumDTO::from)
                .collect(Collectors.toList());
    }

    // Buscar álbum por ID
    public Optional<AlbumDTO> buscarPorId(Long id) {
        return albumRepository.findById(id).map(AlbumDTO::from);
    }

    // Criar um novo álbum
    public AlbumDTO salvar(Album album) {
        Album novoAlbum = albumRepository.save(album);
        return AlbumDTO.from(novoAlbum);
    }

    // Atualizar um álbum existente
    public Optional<AlbumDTO> atualizar(Long id, Album albumAtualizado) {
        return albumRepository.findById(id).map(album -> {
            album.setNome(albumAtualizado.getNome());
            album.setGenero(albumAtualizado.getGenero());
            album.setArtista(albumAtualizado.getArtista());
            album.setAno(albumAtualizado.getAno());
            album.setNotaMedia(albumAtualizado.getNotaMedia());
            album.setImagem(albumAtualizado.getImagem());
            return AlbumDTO.from(albumRepository.save(album));
        });
    }

    // Deletar um álbum por ID
    public boolean deletar(Long id) {
        return albumRepository.findById(id).map(album -> {
            albumRepository.delete(album);
            return true;
        }).orElse(false);
    }

    @Transactional
    public AlbumDTO avaliar(Long idAlbum, Float nota, String comentario) {
        Optional<Album> album = albumRepository.findById(idAlbum);
        Usuario usuario = usuarioRepository.findByEmail(tokenServiceJWT.getEmailUserRequest());
        if (album.isPresent() && usuario != null && !avaliacaoRepository.existsAvaliacaoByUsuario_IdAndAlbum_Id(usuario.getId(), album.get().getId())) {
            album.get().getAvaliacoes().add(avaliacaoRepository.save(
                    Avaliacao.builder().nota(nota)
                    .comentario(comentario).usuario(usuario)
                            .album(album.get()).build()));
            recalcularNotaMediaAlbum(album.get());
            return AlbumDTO.from(albumRepository.save(album.get()));
        } else {
            return null;
        }
    }

    private void recalcularNotaMediaAlbum(Album album) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllByAlbum_Id(album.getId());
        float notaMedia = (float) avaliacoes.stream().mapToDouble(Avaliacao::getNota).average().orElse(0);
        album.setNotaMedia(notaMedia);
        albumRepository.save(album);
    }
}
