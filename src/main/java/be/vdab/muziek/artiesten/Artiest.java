package be.vdab.muziek.artiesten;


import be.vdab.muziek.albums.Album;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "artiesten")
public class Artiest {
    @Id
    private long id;
    private String naam;
    @OneToMany(mappedBy = "artiest")
    @OrderBy("naam")
    private Set<Album> albums;

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Set<Album> getAlbums() {
        return Collections.unmodifiableSet(albums);
    }
}
