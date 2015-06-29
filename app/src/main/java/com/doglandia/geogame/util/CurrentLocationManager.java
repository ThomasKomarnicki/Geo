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

    private Context context;

    private Place currentPlace;

    private boolean currentLocationIsValid = true;

    public CurrentLocationManager(Context context){
        this.context = context;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        currentPlace = new Gson().fromJson(preferences.getString(CURRENT_PLACE,""),Place.class);

        if(currentPlace == null){ // TODO
            Server.getInstance().getLocation(0, new Callback<Place>() {
                @Override
                public void success(Place place, Response response) {
                    currentPlace = place;
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    public Place getCurrentPlace(){
        return currentPlace;
    }

    public void onNewLocationRetrieved(Place place){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_PLACE,new Gson().toJson(place).toString());
        editor.commit();
    }
}
