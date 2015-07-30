package com.doglandia.geogame.fragment.error;

import android.content.Intent;
import android.view.View;

import com.doglandia.geogame.activity.PlaceLocateActivity;
import com.doglandia.geogame.activity.PlaceLocateActivityNewUi;

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
        Intent intent = new Intent(getActivity(), PlaceLocateActivityNewUi.class);
        startActivity(intent);
        getActivity().finish();
    }
}
