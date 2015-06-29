package com.doglandia.geogame.fragment.error;

import android.content.Intent;
import android.view.View;

import com.doglandia.geogame.activity.PlaceLocateActivity;

/**
 * Created by Thomas on 6/27/2015.
 */
public class NoPlaceLocateResultsFragments extends NoDataFragment {
    @Override
    public String getMessageText() {
        return "You don't have any recent location guesses";
    }

    @Override
    public String getButtonText() {
        return "Start Now";
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), PlaceLocateActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
