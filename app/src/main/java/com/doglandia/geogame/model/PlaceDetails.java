package com.doglandia.geogame.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Thomas on 6/16/2015.
 */


public class PlaceDetails {

    Place place;

    int averageDistance;
    int bestDistance;

    List<LatLng> otherGuesses;


    public Place getPlace() {
        return place;
    }

    public int getAverageDistance() {
        return averageDistance;
    }

    public int getBestDistance() {
        return bestDistance;
    }

    public List<LatLng> getOtherGuesses() {
        return otherGuesses;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setAverageDistance(int averageDistance) {
        this.averageDistance = averageDistance;
    }

    public void setBestDistance(int bestDistance) {
        this.bestDistance = bestDistance;
    }

    public void setOtherGuesses(List<LatLng> otherGuesses) {
        this.otherGuesses = otherGuesses;
    }
}
