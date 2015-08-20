package com.doglandia.geogame.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.doglandia.geogame.transmissions.TransmissionFragment;

/**
 * Created by thomaskomarnicki on 8/20/15.
 */
public class TransmissionTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FrameLayout(this));

        TransmissionFragment transmissionFragment = new TransmissionFragment();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content,transmissionFragment)
                .commit();

        transmissionFragment.animateText("TRANSMISSION TEXT 1",
                "LONGER TRANSMISSION TEXT, KEEEPOOO WEEEPOOO KEEEE ARARARARARAR PLEASE NO HELP");
    }
}
