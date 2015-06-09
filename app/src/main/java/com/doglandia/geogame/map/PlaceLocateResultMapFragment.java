package com.doglandia.geogame.map;

import android.graphics.Color;
import android.os.Bundle;

import com.doglandia.server.model.PlaceLocateResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.parceler.Parcels;

/**
 * Created by Thomas on 6/6/2015.
 */
public class PlaceLocateResultMapFragment extends SupportMapFragment {

    private GoogleMap googleMap;

    private PlaceLocateResult placeLocateResult;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        placeLocateResult = Parcels.unwrap(getArguments().getParcelable("locate_result"));
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                PlaceLocateResultMapFragment.this.googleMap = googleMap;
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                showBothLocations();

            }
        });
    }

    public void showBothLocations(){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(placeLocateResult.getGuessedLocation());
        googleMap.moveCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions().position(placeLocateResult.getGuessedLocation()).title("Guessed Location").icon(BitmapDescriptorFactory.defaultMarker()));
        googleMap.addMarker(new MarkerOptions().position(placeLocateResult.getActualLocation().getLatLng()).title("Actual Location").icon(BitmapDescriptorFactory.defaultMarker()));


        LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(placeLocateResult.getGuessedLocation());
        builder.include(placeLocateResult.getActualLocation().getLatLng());
        LatLngBounds bounds = builder.build();

        cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, (int) (32 * getResources().getDisplayMetrics().density));
        googleMap.animateCamera(cameraUpdate);
        showLine();
    }

    public void showLine(){
        PolylineOptions connectingLine = new PolylineOptions()
                .add(placeLocateResult.getGuessedLocation())
                .add(placeLocateResult.getActualLocation().getLatLng())
                .color(Color.BLACK)
                .width(getResources().getDisplayMetrics().density * 5);
        googleMap.addPolyline(connectingLine);
    }
}
