package com.doglandia.geogame.activity.locate;

import android.os.Bundle;

/**
 * Created by Thomas on 8/16/2015.
 */
public class PlaceLocateIntroduction extends PlaceLocateBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchUserCurrentLocation(true);
    }
}
