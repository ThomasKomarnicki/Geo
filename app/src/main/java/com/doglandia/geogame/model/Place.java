package com.doglandia.geogame.model;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

import java.io.IOException;
import java.util.List;

/**
 * Created by Thomas on 6/5/2015.
 */

@Parcel
public class Place {



    int id;

    int userId;

    LatLng latLng;

    private String city;
    private String state;
    private String country;

    public Place(){

    }

    public void geocode(Geocoder geocoder){
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,10);

            if(addressList != null && !addressList.isEmpty()){
                Address address = addressList.get(0);

                state = address.getAdminArea();
                city = address.getLocality();
                country = address.getCountryName();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Place(LatLng latLng){
        setLatLng(latLng);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
