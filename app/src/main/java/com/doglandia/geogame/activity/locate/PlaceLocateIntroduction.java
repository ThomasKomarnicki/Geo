package com.doglandia.geogame.activity.locate;

import android.os.Bundle;
import android.util.Log;

import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.transmissions.TransmissionFragment;
import com.doglandia.geogame.transmissions.TransmissionTextListener;

/**
 * Created by Thomas on 8/16/2015.
 */
public class PlaceLocateIntroduction extends PlaceLocateBaseActivity {

    private static final String TAG = "PlaceLocateIntroduction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchUserCurrentLocation(true);

        TransmissionFragment transmissionFragment = TransmissionFragment.createInstance(4000,
                "Hello Agent, You have been selected to locate designated locations around the world... ",
                "You will be given a transmitted rendering of the location. When you can locate it, press the map button...",
                "Click on a location on the map and then press the \"Locate Here\" button to lock in the location. "
        );
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content,transmissionFragment,"transmission_fragment")
                .commitAllowingStateLoss();


    }

    @Override
    protected void onResume() {
        super.onResume();
        TransmissionFragment transmissionFragment = (TransmissionFragment) getSupportFragmentManager().findFragmentByTag("transmission_fragment");
        if(transmissionFragment != null) {
            transmissionFragment.setTransmissionEndedTextListener(new TransmissionTextListener() {
                @Override
                public void onTransmissionEnd() {
                    UserAuth.finishedFirstRun(PlaceLocateIntroduction.this);
                }

                @Override
                public void onNewTextShown(int textIndex) {
                    Log.d(TAG,"textIndex = "+textIndex);
                    if(textIndex == 1){
                        // todo highlight map button
                    }
                }
            });
        }
    }
}
