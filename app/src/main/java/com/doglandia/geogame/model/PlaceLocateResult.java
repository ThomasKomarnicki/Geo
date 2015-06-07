package com.doglandia.geogame.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thomas on 6/6/2015.
 */
public class PlaceLocateResult {

    private LatLng actualLocation;
    private LatLng guessedLocation;

    public LatLng getActualLocation() {
        return actualLocation;
    }

    public LatLng getGuessedLocation() {
        return guessedLocation;
    }
}
