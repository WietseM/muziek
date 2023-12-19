package be.vdab.muziek.artiesten;

import be.vdab.muziek.albums.Album;
import be.vdab.muziek.albums.AlbumRepository;
import be.vdab.muziek.albums.NieuwAlbum;
import be.vdab.muziek.labels.LabelNietGevondenException;
import be.vdab.muziek.labels.LabelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ArtiestService {
    private final ArtiestRepository artiestRepository;
    private final AlbumRepository albumRepository;
    private final LabelRepository labelRepository;

    public ArtiestService(ArtiestRepository artiestRepository, AlbumRepository albumRepository, LabelRepository labelRepository) {
        this.artiestRepository = artiestRepository;
        this.albumRepository = albumRepository;
        this.labelRepository = labelRepository;
    }

    Optional<Artiest> findById(long id){
        return artiestRepository.findById(id);
    }

    Optional<List<Album>> findByArtiestId(long id){
        return albumRepository.findByArtiest_IdOrderByNaam(id);
    }

    @Transactional
    public void voegAlbumToeAanArtiest(long id, NieuwAlbum nieuwAlbum){
        var artiest = artiestRepository.findById(id).orElseThrow(ArtiestNietGevondenException::new);
        var label = labelRepository.findById(nieuwAlbum.labelId()).orElseThrow(LabelNietGevondenException::new);
        var album = new Album(nieuwAlbum.naam(), nieuwAlbum.jaar(), nieuwAlbum.barcode(), nieuwAlbum.score(), artiest, label);
        artiest.voegAlbumToe(album);
        //artiestRepository.save(artiest); <- werkt niet
        //albumRepository.save(album); <- werkt
    }
}
