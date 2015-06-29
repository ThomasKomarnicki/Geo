package com.doglandia.geogame.map;

import android.os.Bundle;

import com.doglandia.geogame.activity.DiscoverActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thomas on 6/21/2015.
 */
public class DiscoverMapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{

    private GoogleMap googleMap;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        ((DiscoverActivity)getActivity()).onMapLocationClicked(latLng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        onMapClick(latLng);
    }


}
