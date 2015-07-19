package com.doglandia.geogame.server;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceDetails;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Thomas on 6/7/2015.
 */
public interface ServerInterface {

    @GET("/locations/{location_id}")
    void getLocation(@Path("location_id")Integer locationId, Callback<Place> callback);

    @GET("/locations/currentLocation")
    void getCurrentLocation(Callback<Place> callback);

    @POST("/user/{user_id}/guess")
    void postLocateResult(@Path("user_id") Integer userId, @Body PlaceLocateResult placeLocateResult, Callback<Place> callback);

    @GET("/locations/{location_id}/details")
    void getLocationDetails(@Path("location_id")Integer locationId, Callback<PlaceDetails> callback);

    @GET("/user/{user_id}/locationGuesses")
    void getUserLocationGuesses(@Path("user_id")Integer userId, Callback<List<PlaceLocateResult>> callback);

    @GET("user/{user_id}/locations")
    void getUserLocations(@Path("user_id")Integer userId,Callback<ArrayList<Place>> callback);

    @POST("locations")
    void addUserLocation(@Body Place place, Callback<JsonObject> callback);

    @POST("user/google_auth")
    void googleAuth(@Body Map<String,String> body, Callback<User> callback);


}
