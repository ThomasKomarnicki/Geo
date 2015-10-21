package com.doglandia.unearthed.fragment.error;

import android.content.Intent;
import android.view.View;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.activity.DiscoverActivity;


public class NoPlacesFragment extends NoDataFragment {


    @Override
    public String getMessageText() {
        return getString(R.string.no_added_places_error);
    }

    @Override
    public String getButtonText() {
        return getString(R.string.discover_places);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), DiscoverActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
