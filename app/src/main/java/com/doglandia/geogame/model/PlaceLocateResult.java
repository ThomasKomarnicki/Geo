package com.doglandia.geogame.model;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

import java.util.Locale;

/**
 * Created by Thomas on 6/6/2015.
 */

@Parcel
public class PlaceLocateResult {


    Place actualLocation;
    LatLng guessedLocation;

    String distanceString;
    int distance = -1;
//    int score;


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

    public int getScore(){
        return getDistance() / 10000;
    }

    private void setDistance(){
        float[] result = new float[1];
        Location.distanceBetween(getGuessedLocation().latitude,getGuessedLocation().longitude,
                getActualLocation().getLatLng().latitude, getActualLocation().getLatLng().longitude, result);

        distance = (int) result[0];

    }

    public String getDistanceString(){
        if(distanceString == null) {

            String units = "m";
            int convertedDistance = getDistance();
            if (Locale.getDefault().equals(Locale.US)) {
                // convert to miles
                convertedDistance = (int) (getDistance() * 0.000621371192);
                units = "mi";
            } else if (distance > 10000) {
                convertedDistance = getDistance() / 1000;
                units = "km";
            }
            distanceString = convertedDistance + " "+units;
        }
        return distanceString;
    }

    public int getDistance() {
        if(distance < 0){
            setDistance();
        }
        return distance;
    }
}
