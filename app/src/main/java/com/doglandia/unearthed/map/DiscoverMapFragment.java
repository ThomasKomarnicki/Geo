package com.doglandia.unearthed.map;

import android.os.Bundle;

import com.doglandia.unearthed.activity.DiscoverActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

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


    public void resetMapZoom() {
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(5));
    }
}
