package be.vdab.muziek.albums;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
class AlbumService {
    private final AlbumRepository albumRepository;

    AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    List<Album> findAll(){
        return albumRepository.findAll(Sort.by("naam"));
    }


    Optional<Album> findById(long id){
        return albumRepository.findById(id);
    }

    List<Album> findByJaar(int jaar){
        return albumRepository.findByJaarOrderByNaam(jaar);
    }

    @Transactional
    public void wijzigScore(long id, int score){
        albumRepository.findAndLockById(id).orElseThrow(AlbumNietGevondenException::new).setScore(score);
    }


}
