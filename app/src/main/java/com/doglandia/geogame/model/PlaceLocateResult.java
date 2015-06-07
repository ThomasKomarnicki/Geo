package com.doglandia.geogame.model;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

/**
 * Created by Thomas on 6/6/2015.
 */

@Parcel
public class PlaceLocateResult {


    LatLng actualLocation;
    LatLng guessedLocation;

    public LatLng getActualLocation() {
        return actualLocation;
    }

    public LatLng getGuessedLocation() {
        return guessedLocation;
    }

    public void setActualLocation(LatLng actualLocation) {
        this.actualLocation = actualLocation;
    }

    public void setGuessedLocation(LatLng guessedLocation) {
        this.guessedLocation = guessedLocation;
    }
}
