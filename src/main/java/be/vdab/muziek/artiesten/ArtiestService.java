package be.vdab.muziek.artiesten;

import be.vdab.muziek.albums.Album;
import be.vdab.muziek.albums.AlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ArtiestService {
    private final ArtiestRepository artiestRepository;
    private final AlbumRepository albumRepository;

    public ArtiestService(ArtiestRepository artiestRepository, AlbumRepository albumRepository) {
        this.artiestRepository = artiestRepository;
        this.albumRepository = albumRepository;
    }

    Optional<Artiest> findById(long id){
        return artiestRepository.findById(id);
    }

    Optional<List<Album>> findByArtiestId(long id){
        return albumRepository.findByArtiest_IdOrderByNaam(id);
    }

}
