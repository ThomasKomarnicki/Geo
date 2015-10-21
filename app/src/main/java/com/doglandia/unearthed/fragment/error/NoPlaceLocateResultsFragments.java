package com.doglandia.unearthed.fragment.error;

import android.content.Intent;
import android.view.View;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.activity.locate.PlaceLocateDefaultActivity;

public class NoPlaceLocateResultsFragments extends NoDataFragment {
    @Override
    public String getMessageText() {
        return getString(R.string.no_locations_error);
    }

    @Override
    public String getButtonText() {
        return getString(R.string.start_now);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), PlaceLocateDefaultActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
