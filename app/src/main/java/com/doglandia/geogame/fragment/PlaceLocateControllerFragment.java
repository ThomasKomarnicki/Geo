package com.doglandia.geogame.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 7/23/2015.
 */
public class PlaceLocateControllerFragment extends Fragment {

    private static final String TAG = "PlaceLocateController";

    FrameLayout streetViewContainer;
    FrameLayout mapContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(container.getContext());

        streetViewContainer = new FrameLayout(container.getContext());
        streetViewContainer.setBackgroundColor(Color.RED);
        mapContainer = new FrameLayout(container.getContext());
        mapContainer.setBackgroundColor(Color.GREEN);

        frameLayout.addView(streetViewContainer);
        frameLayout.addView(mapContainer);

        return frameLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // animate map container to small square in right corner

        Animation scaleToCorner = AnimationUtils.loadAnimation(getActivity(), R.anim.place_locate_fragment_anim);
        scaleToCorner.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG,"scaleToCorner started");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG,"scaleToCorner end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mapContainer.startAnimation(scaleToCorner);
    }
}
