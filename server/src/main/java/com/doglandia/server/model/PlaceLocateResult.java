package com.doglandia.server.model;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

/**
 * Created by Thomas on 6/6/2015.
 */

@Parcel
public class PlaceLocateResult {


    Place actualLocation;
    LatLng guessedLocation;

    int score;

    public Place getActualLocation() {
        return actualLocation;
    }

    public LatLng getGuessedLocation() {
        return guessedLocation;
    }

    public void setActualLocation(Place actualLocation) {
        this.actualLocation = actualLocation;
    }

    public void setGuessedLocation(LatLng guessedLocation) {
        this.guessedLocation = guessedLocation;
    }
}
