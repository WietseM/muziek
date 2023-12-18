package be.vdab.muziek.albums;


import be.vdab.muziek.tracks.Track;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@RequestMapping("albums")
class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    Stream<AlbumBeknopt> findAll(){
        return albumService.findAll()
                .stream()
                .map(AlbumBeknopt::new);
    }

    @GetMapping("{id}")
    AlbumBeknoptMetTracks findById(@PathVariable long id){
        return albumService.findById(id)
                .map(AlbumBeknoptMetTracks::new)
                .orElseThrow(AlbumNietGevondenException::new);
    }

    @GetMapping(params = "jaar")
    Stream<AlbumBeknopt> findByJaar(int jaar){
        return albumService.findByJaar(jaar)
                .stream()
                .map(AlbumBeknopt::new);
    }

    private record AlbumBeknopt(String naam, String artiest, int jaar){
        AlbumBeknopt(Album album){
            this(album.getNaam(), album.getArtiest().getNaam(), album.getJaar());
        }
    }


    private record AlbumBeknoptMetTracks(String naam, String artiest, int jaar, String label, LocalTime tijd, Set<Track> tracks){
        AlbumBeknoptMetTracks(Album album){
            this(album.getNaam(), album.getArtiest().getNaam(), album.getJaar(), album.getLabel().getNaam(), album.totaleTijd(), album.getTracks());
        }
    }
}
