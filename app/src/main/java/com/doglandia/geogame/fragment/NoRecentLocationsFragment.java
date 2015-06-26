package com.doglandia.geogame.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.doglandia.geogame.R;
import com.doglandia.geogame.activity.PlaceLocateActivity;

/**
 * Created by Thomas on 6/25/2015.
 */
public class NoRecentLocationsFragment extends Fragment {

    private Button startNowButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.no_recent_locations_fragment,null);

        startNowButton = (Button) view.findViewById(R.id.start_now_button);

        startNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceLocateActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;

    }
}
