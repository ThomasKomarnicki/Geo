package com.doglandia.geogame.model.response;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceLocateResult;

/**
 * Created by Thomas on 8/30/2015.
 */
public class LocationGuessResult {

    Place newLocation;

    PlaceLocateResult locationGuessResult;

    public Place getNewLocation() {
        return newLocation;
    }

    public PlaceLocateResult getLocationGuessResult() {
        return locationGuessResult;
    }
}
