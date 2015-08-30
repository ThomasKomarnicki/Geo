package com.doglandia.geogame.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 8/30/2015.
 */
public class CalculatingLocationResultsFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_results_loading_fragment,null);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);
    }

    @Override
    public Dialog getDialog() {
        Dialog dialog = super.getDialog();

        return dialog;
    }
}
