package com.doglandia.unearthed.fragment.error;

import android.os.Bundle;
import android.view.View;

import com.doglandia.unearthed.R;

/**
 * Created by Thomas on 8/9/2015.
 */
public class ButtonLessNoDataFragment extends NoDataFragment {
    @Override
    public String getMessageText() {
        return getString(R.string.could_not_connect);
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
