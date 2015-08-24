package com.doglandia.geogame.activity.locate;

import android.os.Bundle;

import com.doglandia.geogame.transmissions.TransmissionFragment;
import com.doglandia.geogame.transmissions.TransmissionTextListener;

/**
 * Created by Thomas on 8/16/2015.
 */
public class PlaceLocateIntroduction extends PlaceLocateBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchUserCurrentLocation(true);

        TransmissionFragment transmissionFragment = TransmissionFragment.createInstance(
                "Hello Agent, You have been selected to locate designated locations around the world... ",
                "You will be given a transmitted rendering of the location. When you can locate it, press the map button...",
                "click on a location on the map and then press the \"Select this Location\" button to lock in the location. "
        );
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content,transmissionFragment)
                .commitAllowingStateLoss();



    }
}
