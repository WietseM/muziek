package be.vdab.muziek.artiesten;

import be.vdab.muziek.albums.Album;
import be.vdab.muziek.albums.AlbumRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("artiesten")
public class ArtiestController {
    private final ArtiestService artiestService;

    public ArtiestController(ArtiestService artiestService) {
        this.artiestService = artiestService;
    }

    @GetMapping("{id}/albums")
    Artiest findById(@PathVariable long id) {
        return artiestService.findById(id)
                .orElseThrow(ArtiestNietGevondenException::new);
    }
}
