package be.vdab.muziek.albums;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class AlbumNietGevondenException extends RuntimeException{
    AlbumNietGevondenException(){
        super("Album niet gevonden.");
    }
}
