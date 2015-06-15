package com.doglandia.geogame.server;

import android.location.Geocoder;

import com.doglandia.geogame.GeoApplication;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Path;

/**
 * Created by Thomas on 6/7/2015.
 */
public class LocalMockServer implements ServerInterface {

    List<Place> places;
    List<PlaceLocateResult> placeLocateResults;


    public LocalMockServer(){
        places = new ArrayList<>();
        Geocoder geocoder = new Geocoder(GeoApplication.getContext());
        placeLocateResults = new ArrayList<>();

        Place place = new Place(new LatLng(27.90412,-82.672321));
        place.setId(0);
        place.geocode(geocoder);
        places.add(place);

        place = new Place(new LatLng(27.814931,-82.630792));
        place.setId(1);
        place.geocode(geocoder);
        places.add(place);

        place = new Place(new LatLng(27.818742,-82.63375));
        place.setId(2);
        place.geocode(geocoder);
        places.add(place);
    }




    @Override
    public void locationGuess(@Path("user_id") Integer userId, @Body PlaceLocateResult placeLocateResult, Callback<Place> callback) {
        placeLocateResults.add(placeLocateResult);
        callback.success(getRandomPlace(placeLocateResult.getActualLocation().getId()), null);
    }

    @Override
    public void getNewLocation(@Path("user_id") Integer userId, Callback<Place> callback) {
        callback.success(getRandomPlace(0),null);
    }

    @Override
    public void getUserLocations(@Path("user_id") Integer userId, Callback<List<Place>> callback) {
        callback.success(places,null);
    }

    @Override
    public void getUserPlaceLocateResults(@Path("user_id") Integer userId, Callback<List<PlaceLocateResult>> callback) {
        callback.success(placeLocateResults,null);
    }

    private Place getRandomPlace(int lastPlaceId){

        Random random = new Random();
        int nextId = random.nextInt(places.size());
        if(nextId == lastPlaceId){
            nextId = 0;
        }
        return places.get(nextId);
    }
}
