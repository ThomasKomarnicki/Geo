package com.doglandia.geogame.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Thomas on 9/6/2015.
 */

@Parcel
public class UserProfileStats {

    int level;
    int locationCount;
    int locationGuessCount;

    @SerializedName("easiest_location_avg")
    int easiestLocationAverage;
    Place easiestLocation;

    @SerializedName("hardest_location_avg")
    int hardestLocationAverage;
    Place hardestLocation;

    int bestGuess;
    Place bestGuessLocation;


    public int getLevel() {
        return level;
    }

    public int getLocationCount() {
        return locationCount;
    }

    public int getLocationGuessCount() {
        return locationGuessCount;
    }

    public int getEasiestLocationAverage() {
        return easiestLocationAverage;
    }

    public Place getEasiestLocation() {
        return easiestLocation;
    }

    public int getHardestLocationAverage() {
        return hardestLocationAverage;
    }

    public Place getHardestLocation() {
        return hardestLocation;
    }

    public int getBestGuess() {
        return bestGuess;
    }

    public Place getBestGuessLocation() {
        return bestGuessLocation;
    }
}
