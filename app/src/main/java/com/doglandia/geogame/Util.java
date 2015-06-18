package com.doglandia.geogame;

import android.content.Context;

import java.util.Locale;

/**
 * Created by Thomas on 6/17/2015.
 */
public class Util {

    public static boolean isUsingMiles(){
        return Locale.getDefault().equals(Locale.US);
    }

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
}
