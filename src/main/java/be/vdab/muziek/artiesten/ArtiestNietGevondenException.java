package be.vdab.muziek.artiesten;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ArtiestNietGevondenException extends RuntimeException{
    ArtiestNietGevondenException(){
        super("Artiest niet gevonden.");
    }
}
