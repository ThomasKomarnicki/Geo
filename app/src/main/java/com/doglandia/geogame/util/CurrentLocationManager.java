package com.doglandia.geogame.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.server.Server;
import com.google.gson.Gson;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/28/2015.
 *
 * not for actual GPS location but for location to guess
 */
public class CurrentLocationManager {

    private static final String CURRENT_PLACE = "current_place";
    private static final String CURRENT_PLACE_ID = "current_place_id";

    private Context context;

    private Place currentPlace;

    private boolean currentLocationIsValid = true;

    public CurrentLocationManager(Context context){
        this.context = context;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        currentPlace = Server.serverGson.fromJson(preferences.getString(CURRENT_PLACE,""),Place.class);

    }

    public Place getCurrentPlace(){
        return currentPlace;
    }

    public void setCurrentPlace(Place place){
        currentPlace = place;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_PLACE,Server.serverGson.toJson(place));
        editor.commit();
        setCurrentPlaceId(place.getId());
    }

    public void setCurrentPlaceId(int placeId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CURRENT_PLACE_ID,placeId);
    }

    public int getCurrentPlaceId(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(CURRENT_PLACE_ID,-1);
    }

    public void onNewLocationRetrieved(Place place){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_PLACE,new Gson().toJson(place));
        editor.commit();
    }

    public void fetchLocation(final Callback<Place> callback){
        Server.getInstance().getLocation(getCurrentPlaceId(), new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
                onNewLocationRetrieved(place);
                callback.success(place,response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error);
            }
        });
    }
}
