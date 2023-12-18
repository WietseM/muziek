package be.vdab.muziek.albums;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Override
    @EntityGraph(attributePaths = "artiest")
    List<Album> findAll(Sort sort);

    @EntityGraph(attributePaths = "artiest")
    List<Album> findByJaarOrderByNaam(int jaar);
}
