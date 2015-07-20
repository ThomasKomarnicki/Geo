package com.doglandia.geogame.server;

import android.location.Geocoder;

import com.doglandia.geogame.GeoApplication;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceDetails;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Path;

/**
 * Created by Thomas on 6/7/2015.
 */
public class LocalMockServer implements ServerInterface {

    ArrayList<Place> places;
    List<PlaceLocateResult> placeLocateResults;
    PlaceDetails placeDetails;


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

        placeDetails = new PlaceDetails();

    }

    private List<LatLng> generateRandomLatLons(int count){
        List<LatLng> randoms = new ArrayList<>();
        Random randomLat = new Random();
        Random randomLon = new Random();
        for(int i = 0; i < count; i++){
            randoms.add(new LatLng((randomLat.nextDouble()*180)-90,(randomLon.nextDouble()*360)-180));
        }

        return randoms;
    }


    private Place getRandomPlace(int lastPlaceId){

        Random random = new Random();
        int nextId = random.nextInt(places.size());
        if(nextId == lastPlaceId){
            nextId = 0;
        }
        return places.get(nextId);
    }

    @Override
    public void getLocation(@Path("location_id") Integer locationId, Callback<Place> callback) {
        callback.success(places.get(locationId),null);
    }

    @Override
    public void getCurrentLocation(Callback<Place> callback) {
        callback.success(getRandomPlace(0),null);
    }

    @Override
    public void postLocateResult(@Body PlaceLocateResult placeLocateResult, Callback<Place> callback) {

    }

//    @Override
//    public void postLocateResult(@Body PlaceLocateResult placeLocateResult, Callback<Place> callback) {
//        placeLocateResults.add(placeLocateResult);
//        callback.success(getRandomPlace(placeLocateResult.getActualLocation().getId()), null);
//    }

    @Override
    public void getLocationDetails(@Path("location_id") Integer locationId, Callback<PlaceDetails> callback) {
        PlaceDetails placeDetails = new PlaceDetails();
        placeDetails.setPlace(places.get(locationId));
        placeDetails.setAverageDistance(68000);
        placeDetails.setBestDistance(12000);
        placeDetails.setOtherGuesses(generateRandomLatLons(10));
        callback.success(placeDetails,null);
    }

    @Override
    public void getUserLocationGuesses(@Path("user_id") Integer userId, Callback<List<PlaceLocateResult>> callback) {
        callback.success(placeLocateResults,null);
    }

    @Override
    public void getUserLocations(@Path("user_id") Integer userId, Callback<ArrayList<Place>> callback) {
        callback.success(places,null);
//        callback.success(new ArrayList<Place>(),null);
    }

    @Override
    public void addUserLocation(@Body Place place, Callback<JsonObject> callback) {

    }

    @Override
    public void googleAuth(@Body Map<String, String> body, Callback<User> callback) {

    }

//    @Override
//    public void addUserLocation(@Path("user_id") Integer userId, @Body LatLng latLng, Callback<JsonObject> callback) {
//        Place place = new Place(latLng);
//        place.geocode(new Geocoder(GeoApplication.getContext()));
//        place.setId(places.size());
//        places.add(place);
//
//        callback.success(null,null);
//    }
}
