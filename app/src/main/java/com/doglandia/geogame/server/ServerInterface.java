package com.doglandia.geogame.server;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceDetails;
import com.doglandia.geogame.model.PlaceLocateResult;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Thomas on 6/7/2015.
 */
public interface ServerInterface {

    @POST("/locationGuess/{user_id}")// send placeLocate result and get new Place
    void locationGuess(@Path("user_id")Integer userId, @Body PlaceLocateResult placeLocateResult, Callback<Place> callback);

    @POST("/getNewLocation/{user_id}") // get new place based on userId
    void getNewLocation(@Path("user_id")Integer userId,Callback<Place> callback);

    @GET("/userLocations/{user_id}")
    void getUserLocations(@Path("user_id")Integer userId, Callback<List<Place>> callback);

    @GET("/userPlaceLocateResults/{user_id}")
    void getUserPlaceLocateResults(@Path("user_id")Integer userId,Callback<List<PlaceLocateResult>> callback);

    @GET("/placeDetails/{place_id}")
    void getPlaceDetails(@Path("user_id")Integer placeId, Callback<PlaceDetails> callback);
}
