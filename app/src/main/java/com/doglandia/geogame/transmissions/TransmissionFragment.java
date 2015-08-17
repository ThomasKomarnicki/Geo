package com.doglandia.geogame.transmissions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 8/16/2015.
 */
public class TransmissionFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transmission_content,null);


        return view;
    }
}
