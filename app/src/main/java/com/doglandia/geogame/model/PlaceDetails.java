package com.doglandia.geogame.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class PlaceDetails {

    Place place;

    int averageDistance;
    int bestDistance;

    List<LatLng> locationGuesses;


    public Place getPlace() {
        return place;
    }

    public int getAverageDistance() {
        return averageDistance;
    }

    public int getBestDistance() {
        return bestDistance;
    }

    public List<LatLng> getLocationGuesses() {
        return locationGuesses;
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

    public void setLocationGuesses(List<LatLng> locationGuesses) {
        this.locationGuesses = locationGuesses;
    }
}
