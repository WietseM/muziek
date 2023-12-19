package be.vdab.muziek.artiesten;

import be.vdab.muziek.albums.Album;
import be.vdab.muziek.albums.AlbumRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    private record AlbumsBeknopt(String naam, int jaar){
        AlbumsBeknopt(Album album){
            this(album.getNaam(), album.getJaar());
        }
    }
}
