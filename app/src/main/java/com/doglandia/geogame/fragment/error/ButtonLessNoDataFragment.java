package com.doglandia.geogame.fragment.error;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Thomas on 8/9/2015.
 */
public class ButtonLessNoDataFragment extends NoDataFragment {
    @Override
    public String getMessageText() {
        return "Could not connect, check your internet connection or try again later";
    }

    @Override
    public String getButtonText() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startNowButton.setVisibility(View.GONE);
    }
}
