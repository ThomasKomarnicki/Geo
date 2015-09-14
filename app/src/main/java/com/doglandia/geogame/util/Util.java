package com.doglandia.geogame.util;

import android.content.Context;
import android.location.Geocoder;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceLocateResult;

import java.util.List;
import java.util.Locale;

/**
 * Created by Thomas on 6/17/2015.
 */
public class Util {

    private static final String TAG = "Util";

    public static boolean isUsingMiles(){
        return Locale.getDefault().equals(Locale.US);
    }

    public static final float PRIMARY_COLOR_HUE = 136f;

    public static String getDistanceDisplay(int distance){
        String units;
        if(isUsingMiles()){
            distance = (int) (distance * 0.000621371192);
            units = "mi";
        }else if(distance < 10000){
            units = "m";
        }else{
            distance = distance/1000;
            units = "km";
        }
        return distance + " "+units;
    }

    public static void GeoCodePlaces(Place[] places, Context context){
//        long startTime = System.currentTimeMillis();
        Geocoder geocoder = new Geocoder(context);
        for(Place place : places){
            if(place != null) {
                place.geocode(geocoder);
            }
        }
//        Log.d(TAG, "geocode time for "+places.size()+" places: "+(System.currentTimeMillis() - startTime));
    }

    public static void GeoCodeLocationGuess(List<PlaceLocateResult> places, Context context){
//        long startTime = System.currentTimeMillis();
        Geocoder geocoder = new Geocoder(context);
        for(PlaceLocateResult place : places){
            place.getActualLocation().geocode(geocoder);
        }
//        Log.d(TAG, "geocode time for "+places.size()+" places: "+(System.currentTimeMillis() - startTime));
    }
}
