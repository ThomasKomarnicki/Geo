package com.doglandia.geogame.map;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by Thomas on 6/6/2015.
 */
public class PlaceLocateResultMapFragment extends SupportMapFragment {

    private GoogleMap googleMap;

    private LatLng guessLocation;
    private LatLng actualLocation;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        guessLocation = getArguments().getParcelable("guessed_location");
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                PlaceLocateResultMapFragment.this.googleMap = googleMap;
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(guessLocation);
                googleMap.moveCamera(cameraUpdate);
                googleMap.addMarker(new MarkerOptions().position(guessLocation).title("Guessed Location").icon(BitmapDescriptorFactory.defaultMarker()));
            }
        });
    }

    public void showBothLocations(LatLng actualLocation){
        this.actualLocation = actualLocation;
        LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(guessLocation);
        builder.include(actualLocation);
        LatLngBounds bounds = builder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, (int) (16 * getResources().getDisplayMetrics().density));
        googleMap.animateCamera(cameraUpdate);
        showLine();
    }

    public void showLine(){
        PolylineOptions connectingLine = new PolylineOptions()
                .add(guessLocation)
                .add(actualLocation)
                .color(Color.BLACK)
                .width(getResources().getDisplayMetrics().density * 5);
        googleMap.addPolyline(connectingLine);
    }
}
