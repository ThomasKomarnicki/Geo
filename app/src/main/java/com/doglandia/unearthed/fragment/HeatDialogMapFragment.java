package com.doglandia.unearthed.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.activity.MyPlacesActivity;
import com.doglandia.unearthed.map.PlaceHeatMapFragment;
import com.doglandia.unearthed.model.PlaceDetails;
import com.google.android.gms.maps.GoogleMap;

/**
 * outer container fragment for place heat map fragment
 */
public class HeatDialogMapFragment extends Fragment {

    private FrameLayout heatMapHolder;

    private PlaceHeatMapFragment heatMapFragment;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heat_dialog_map_fragment,null);

        heatMapHolder = (FrameLayout) view.findViewById(R.id.dialog_heat_map_holder);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        heatMapFragment = new PlaceHeatMapFragment(){
            @Override
            public void configMapSettings(GoogleMap googleMap) {
                googleMap.getUiSettings().setMapToolbarEnabled(false);
                googleMap.getUiSettings().setTiltGesturesEnabled(false);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);
            }
        };
        if(heatMapHolder.getChildCount() == 0) {
            getChildFragmentManager().beginTransaction().replace(heatMapHolder.getId(), heatMapFragment).commit();
        }


        PlaceDetailsFragment placeDetailsFragment = (PlaceDetailsFragment) getFragmentManager().findFragmentByTag("place_details_fragment");
        if(placeDetailsFragment == null){ // should only be null if parent activity is my places activity
            placeDetailsFragment = ((MyPlacesActivity)getActivity()).getPlaceDetailsFragment();
        }
        if(placeDetailsFragment != null) {
            PlaceDetails placeDetails = placeDetailsFragment.getPlaceDetailsObject();
            if(placeDetails != null){
                heatMapFragment.showHeat(placeDetails);
            }else {
                placeDetailsFragment.setOnPlaceDetailsLoadedListener(new PlaceDetailsFragment.OnPlaceDetailsLoadedListener() {
                    @Override
                    public void onPlaceDetailsLoaded(PlaceDetails placeDetails) {
                        Log.d("HeatDialogMapFragment", "on Place details loaded");
                        heatMapFragment.showHeat(placeDetails);
                    }
                });
            }
        }
    }
}
