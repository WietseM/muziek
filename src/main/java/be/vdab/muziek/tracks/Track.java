package be.vdab.muziek.tracks;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public class Track {
    private String naam;
    private LocalTime tijd;

    public String getNaam() {
        return naam;
    }

    public LocalTime getTijd() {
        return tijd;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Track track && naam.equals(track.naam);
    }


    @Override
    public int hashCode() {
        return naam.hashCode();
    }
}
