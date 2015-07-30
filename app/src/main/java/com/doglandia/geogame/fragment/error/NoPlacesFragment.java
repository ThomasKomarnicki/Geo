package com.doglandia.geogame.fragment.error;

import android.content.Intent;
import android.view.View;

import com.doglandia.geogame.activity.DiscoverActivity;


public class NoPlacesFragment extends NoDataFragment {


    @Override
    public String getMessageText() {
        return "You haven't added any places yet";
    }

    @Override
    public String getButtonText() {
        return "Discover Places Now";
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), DiscoverActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
