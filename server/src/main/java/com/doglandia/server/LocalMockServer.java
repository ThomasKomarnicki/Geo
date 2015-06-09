package com.doglandia.server;

import com.doglandia.server.model.Place;
import com.doglandia.server.model.PlaceLocateResult;
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

    List<Place> places = new ArrayList<>();
    public LocalMockServer(){
        Place place = new Place(new LatLng(27.90412,-82.672321));
        place.setId(0);
        places.add(place);
        place = new Place(new LatLng(27.814931,-82.630792));
        place.setId(1);
        places.add(place);
        place = new Place(new LatLng(27.818742,-82.63375));
        place.setId(2);
        places.add(place);
    }


    @Override
    public void locationGuess(@Path("user_id") Integer userId, @Body PlaceLocateResult placeLocateResult, Callback<Place> callback) {

        callback.success(getRandomPlace(placeLocateResult.getActualLocation().getId()), null);
    }

    @Override
    public void getNewLocation(@Path("user_id") Integer userId, Callback<Place> callback) {
        callback.success(getRandomPlace(0),null);
    }

    private Place getRandomPlace(int lastPlaceId){

        Random random = new Random();
        int nextId = random.nextInt(places.size())-1;
        if(nextId == lastPlaceId){
            nextId = 0;
        }
        return places.get(nextId);
    }
}
