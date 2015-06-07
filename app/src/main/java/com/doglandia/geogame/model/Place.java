package com.doglandia.geogame.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thomas on 6/5/2015.
 */
public class Place {

    LatLng latLng;

    public Place(LatLng latLng){
        setLatLng(latLng);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
