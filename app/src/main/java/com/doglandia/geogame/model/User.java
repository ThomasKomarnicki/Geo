package com.doglandia.geogame.model;

import org.parceler.Parcel;

/**
 * Created by Thomas on 7/18/2015.
 */
@Parcel
public class User {

    int id;
    int currentLocationId;
    String googleAuthId;
    String otherIdentifier;

    public int getId() {
        return id;
    }

    public int getCurrentLocationId() {
        return currentLocationId;
    }

    public String getGoogleAuthId() {
        return googleAuthId;
    }

    public String getOtherIdentifier() {
        return otherIdentifier;
    }
}
