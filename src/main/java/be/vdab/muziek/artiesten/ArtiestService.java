package be.vdab.muziek.artiesten;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ArtiestService {
    private final ArtiestRepository artiestRepository;


    public ArtiestService(ArtiestRepository artiestRepository) {
        this.artiestRepository = artiestRepository;
    }

    Optional<Artiest> findById(long id){
        return artiestRepository.findById(id);
    }
}
