package be.vdab.muziek.artiesten;

import be.vdab.muziek.albums.Album;
import be.vdab.muziek.albums.AlbumRepository;
import be.vdab.muziek.albums.NieuwAlbum;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;



@RestController
@RequestMapping("artiesten")
public class ArtiestController {
    private final ArtiestService artiestService;

    public ArtiestController(ArtiestService artiestService) {
        this.artiestService = artiestService;
    }

    @GetMapping("{id}/albums")
    Stream<AlbumsBeknopt> findById(@PathVariable long id) {
        return artiestService.findById(id)
                .orElseThrow(ArtiestNietGevondenException::new)
                .getAlbums()
                .stream()
                .map(AlbumsBeknopt::new);
    }

    @GetMapping("{id}")
    Stream<AlbumsBeknopt> findByArtiestId(@PathVariable long id) {
        return artiestService.findByArtiestId(id)
                .orElseThrow(ArtiestNietGevondenException::new)
                .stream()
                .map(AlbumsBeknopt::new);
    }

    @PostMapping("{artiestId}")
    void voegAlbumToeAanArtiest(@PathVariable long artiestId, @RequestBody @Valid NieuwAlbum nieuwAlbum){
        artiestService.voegAlbumToeAanArtiest(artiestId, nieuwAlbum);
    }


    private record AlbumsBeknopt(String naam, int jaar){
        AlbumsBeknopt(Album album){
            this(album.getNaam(), album.getJaar());
        }
    }
}
