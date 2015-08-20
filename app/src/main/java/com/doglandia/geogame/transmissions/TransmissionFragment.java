package com.doglandia.geogame.transmissions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doglandia.geogame.R;
import com.doglandia.geogame.transmissions.view.TransmissionConsole;

/**
 * Extend this fragment to create series of transmissions, each transmission is started with
 * animateText
 */
public class TransmissionFragment extends Fragment implements View.OnClickListener, TransmissionTextListener {


    private TransmissionConsole transmissionConsole;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transmission_content,null);

        transmissionConsole = (TransmissionConsole) view.findViewById(R.id.transmission_console);

        view.setClickable(true);
        view.setOnClickListener(this);

        return view;
    }

    public void animateText(String... text){
        transmissionConsole.animateText(text);
    }

    @Override
    public void onClick(View v) {
        // continue / end current animation
        transmissionConsole.onScreenClick();

    }

    @Override
    public void onTransmissionEnd() {

    }
}
