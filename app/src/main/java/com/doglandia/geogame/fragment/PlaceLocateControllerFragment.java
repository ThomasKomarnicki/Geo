package com.doglandia.geogame.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.doglandia.geogame.Animation.AnimatorFrameLayout;
import com.doglandia.geogame.R;
import com.doglandia.geogame.activity.PlaceLocateActivityNewUi;
import com.doglandia.geogame.map.LocatingMapFragment;
import com.doglandia.geogame.map.StreetViewMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thomas on 7/23/2015.
 */
public class PlaceLocateControllerFragment extends Fragment {

    private static final String TAG = "PlaceLocateController";

    private static final String LOCATING_FRAGMENT_VISIBLE = "locating_fragment_visible";

//    FrameLayout streetViewContainer;
//    AnimatorFrameLayout mapContainer;
    private FrameLayout mainContent;
    private FloatingActionButton toggle;

    private LocatingMapFragment locatingMapFragment;
    private StreetViewMapFragment streetViewMapFragment;

    private View locatingMapContainer;
    private View streetMapContainer;

//    private LatLng currentLatLng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_locate_controller,null);

//        streetViewContainer = (FrameLayout) view.findViewById(R.id.place_locate_controller_street_container);
//        mapContainer = (AnimatorFrameLayout) view.findViewById(R.id.place_locate_controller_map_container);

        mainContent = (FrameLayout) view.findViewById(R.id.place_locate_controller_content);
        toggle = (FloatingActionButton) view.findViewById(R.id.plcate_locate_controller_toggle);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFragments();
            }
        });

        locatingMapContainer = view.findViewById(R.id.place_locate_map_container);
        streetMapContainer = view.findViewById(R.id.place_locate_street_container);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        if(streetViewMapFragment == null){
//            streetViewMapFragment = new StreetViewMapFragment();
//        }
//        if(locatingMapFragment == null){
//            locatingMapFragment = new LocatingMapFragment();
//        }

        streetViewMapFragment = (StreetViewMapFragment) getChildFragmentManager().findFragmentById(R.id.place_locate_street_map_fragment);
        locatingMapFragment = (LocatingMapFragment) getChildFragmentManager().findFragmentById(R.id.place_locate_locating_map_fragment);

//        getChildFragmentManager()
//                .beginTransaction()
//                .add(mainContent.getId(), streetViewMapFragment, "street_map_fragment")
//                .commit();

        if(savedInstanceState != null && savedInstanceState.getBoolean(LOCATING_FRAGMENT_VISIBLE,false)){
            toggleFragments();
        }

    }

    private void toggleFragments(){
//        if(locatingMapFragment.isAdded()){
//            getChildFragmentManager()
//                    .beginTransaction()
//                    .replace(mainContent.getId(), streetViewMapFragment, "street_map_fragment")
//                    .commit();
//        }else{
//            getChildFragmentManager()
//                    .beginTransaction()
//                    .replace(mainContent.getId(), locatingMapFragment, "locating_map_fragment")
//                    .commit();
//        }

        if(locatingMapContainer.getVisibility() == View.VISIBLE){
            locatingMapContainer.setVisibility(View.GONE);
            streetMapContainer.setVisibility(View.VISIBLE);
        }else{
            locatingMapContainer.setVisibility(View.VISIBLE);
            streetMapContainer.setVisibility(View.GONE);
        }
    }

    public void setPosition(final LatLng latLng) {
        if(streetViewMapFragment != null) {
            streetViewMapFragment.setPosition(latLng);
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    streetViewMapFragment.setPosition(latLng);
                }
            }, 100);
        }
    }

    public void clearMap() {
        locatingMapFragment.clearMap();
    }

    public void reset(){
        clearMap();
//        if(locatingMapFragment.isAdded()) {
//            getChildFragmentManager()
//                    .beginTransaction()
//                    .replace(mainContent.getId(), streetViewMapFragment, "street_map_fragment")
//                    .commit();
//        }

        locatingMapContainer.setVisibility(View.GONE);
        streetMapContainer.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(locatingMapContainer.getVisibility() == View.VISIBLE){
            outState.putBoolean(LOCATING_FRAGMENT_VISIBLE,true);
        }
        super.onSaveInstanceState(outState);
    }

}
