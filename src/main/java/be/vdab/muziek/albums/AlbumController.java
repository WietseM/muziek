package be.vdab.muziek.albums;


import be.vdab.muziek.tracks.Track;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

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


    @PatchMapping("{id}/score")
    void wijzigScore(@PathVariable long id, @RequestBody @NotNull @Min(0) @Max(10) int score){
        albumService.wijzigScore(id, score);
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
