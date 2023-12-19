package be.vdab.muziek.albums;

import be.vdab.muziek.artiesten.Artiest;
import be.vdab.muziek.labels.Label;
import be.vdab.muziek.tracks.Track;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    private long id;
    private String naam;
    private int jaar;
    private long barcode;
    private int score;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "artiestId")
    private Artiest artiest;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "labelId")
    private Label label;
    @ElementCollection
    @CollectionTable(name = "tracks",
    joinColumns = @JoinColumn(name = "albumId"))
    private Set<Track> tracks = new LinkedHashSet<>();

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getJaar() {
        return jaar;
    }

    public long getBarcode() {
        return barcode;
    }

    public int getScore() {
        return score;
    }

    public Artiest getArtiest() {
        return artiest;
    }

    public Label getLabel() {
        return label;
    }

    public Set<Track> getTracks() {
        return Collections.unmodifiableSet(tracks);
    }

    public void setScore(int score) {
        this.score = score;
        System.out.println(score);
    }

    public LocalTime totaleTijd(){
        return tracks.stream()
                .map(Track::getTijd)
                .reduce(LocalTime.of(0,0,0),
                        (vorigeSom, tijd) -> vorigeSom.plusHours(tijd.getHour()).plusMinutes(tijd.getMinute()).plusSeconds(tijd.getSecond()));
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Album album && barcode == album.barcode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }
}
