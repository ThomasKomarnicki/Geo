package com.doglandia.unearthed.server;

import com.doglandia.unearthed.model.Place;
import com.doglandia.unearthed.model.PlaceDetails;
import com.doglandia.unearthed.model.PlaceLocateResult;
import com.doglandia.unearthed.model.SlideShowInfo;
import com.doglandia.unearthed.model.User;
import com.doglandia.unearthed.model.UserProfileStats;
import com.doglandia.unearthed.model.response.LocationGuessResult;

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
    void postLocateResult(@Body PlaceLocateResult placeLocateResult, @Query("auth_token") String authToken,Callback<LocationGuessResult> callback);

    @GET("/locations/{location_id}/details")
    void getLocationDetails(@Path("location_id")Integer locationId, Callback<PlaceDetails> callback);

    @GET("/users/{user_id}/locationGuesses/")
    void getUserLocationGuesses(@Path("user_id")Integer userId,  @Query("page")Integer page, Callback<List<PlaceLocateResult>> callback);

    @GET("/users/{user_id}/locations/")
    void getUserLocations(@Path("user_id")Integer userId, @Query("page")Integer page,Callback<ArrayList<Place>> callback);

    @POST("/locations/")
    void addUserLocation(@Body Place place, @Query("auth_token") String authToken, Callback<Place> callback);

    @POST("/users/google_auth/")
    void googleAuth(@Body Map<String,String> body, Callback<User> callback);

    @POST("/users/")
    void noAuthUser(@Body Map<String,String> body, Callback<User> callback);

    @GET("/users/{user_id}/profile_stats/")
    void getProfileStats(@Path("user_id")Integer userId, @Query("auth_token")String authToken, Callback<UserProfileStats> callback);

    @GET("/slideshow_info")
    void getSlideShowInfo(Callback<SlideShowInfo> callback);
}
