package be.vdab.muziek.albums;

import be.vdab.muziek.artiesten.Artiest;

public record NieuwAlbum(String naam, int jaar, long barcode, int score, long labelId) {
}
