package com.doglandia.geogame.model;

import org.parceler.Parcel;

/**
 * Created by Thomas on 7/18/2015.
 */
@Parcel
public class User {

    int id;
    int currentLocation;
    String googleAuthId;
    String otherIdentifier;

    public int getId() {
        return id;
    }

    public int getCurrentLocationId() {
        return currentLocation;
    }

    public String getGoogleAuthId() {
        return googleAuthId;
    }

    public String getOtherIdentifier() {
        return otherIdentifier;
    }

    public void setCurrentLocationId(int currentLocationId) {
        this.currentLocation = currentLocationId;
    }

    public void setOtherIdentifier(String otherIdentifier) {
        this.otherIdentifier = otherIdentifier;
    }

    public void setId(int id) {
        this.id = id;
    }
}
