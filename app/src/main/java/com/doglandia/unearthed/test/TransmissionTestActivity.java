package com.doglandia.unearthed.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.doglandia.unearthed.transmissions.TransmissionFragment;
import com.doglandia.unearthed.transmissions.TransmissionTextListener;

/**
 * Created by thomaskomarnicki on 8/20/15.
 */
public class TransmissionTestActivity extends AppCompatActivity {

    TransmissionFragment transmissionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FrameLayout(this));

        transmissionFragment = TransmissionFragment.createInstance(4000,"TRANSMISSION TEXT 1",
                "LONGER TRANSMISSION TEXT, KEEEPOOO WEEEPOOO KEEEE ARARARARAR PLEASE NO HELP");
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content,transmissionFragment)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

//        transmissionFragment.animateText("TRANSMISSION TEXT 1",
//                "LONGER TRANSMISSION TEXT, KEEEPOOO WEEEPOOO KEEEE ARARARARARAR PLEASE NO HELP");
        transmissionFragment.setTransmissionEndedTextListener(new TransmissionTextListener() {
            @Override
            public void onTransmissionEnd() {
                getSupportFragmentManager().beginTransaction().remove(transmissionFragment).commitAllowingStateLoss();
            }

            @Override
            public void onNewTextShown(int textIndex) {

            }
        });
    }
}
