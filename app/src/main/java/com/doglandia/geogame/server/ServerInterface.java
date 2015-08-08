package com.doglandia.geogame.server;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceDetails;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.model.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Thomas on 6/7/2015.
 */
public interface ServerInterface {

    @GET("/locations/{location_id}/")
    void getLocation(@Path("location_id")Integer locationId, Callback<Place> callback);

    @GET("/locations/currentLocation/")
    void getCurrentLocation(Callback<Place> callback);

    @POST("/locationGuess/")
    void postLocateResult(@Body PlaceLocateResult placeLocateResult, Callback<Place> callback);

    @GET("/locations/{location_id}/details")
    void getLocationDetails(@Path("location_id")Integer locationId, Callback<PlaceDetails> callback);

    @GET("/users/{user_id}/locationGuesses/")
    void getUserLocationGuesses(@Path("user_id")Integer userId,  @Query("page")Integer page, Callback<List<PlaceLocateResult>> callback);

    @GET("/users/{user_id}/locations/")
    void getUserLocations(@Path("user_id")Integer userId, @Query("page")Integer page,Callback<ArrayList<Place>> callback);

    @POST("/locations/")
    void addUserLocation(@Body Place place, Callback<Place> callback);

    @POST("/users/google_auth/")
    void googleAuth(@Body Map<String,String> body, Callback<User> callback);

    @POST("/users/")
    void noAuthUser(@Body Map<String,String> body, Callback<User> callback);


}
