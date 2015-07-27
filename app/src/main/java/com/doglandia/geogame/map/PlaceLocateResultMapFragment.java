package com.doglandia.geogame.map;

import android.graphics.Color;
import android.os.Bundle;

import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.util.Util;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
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
        if(getArguments() != null && getArguments().containsKey("locate_result")) {
            placeLocateResult = Parcels.unwrap(getArguments().getParcelable("locate_result"));
        }
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                PlaceLocateResultMapFragment.this.googleMap = googleMap;
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return true;
                    }
                });
                if(placeLocateResult != null) {
                    showPlaceLocateResult(placeLocateResult);
                }

            }
        });
    }

    public void showPlaceLocateResult(PlaceLocateResult placeLocateResult){
        googleMap.clear();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(placeLocateResult.getGuessedLocation());
        googleMap.moveCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions().position(placeLocateResult.getGuessedLocation()).title("Guessed Location").icon(BitmapDescriptorFactory.defaultMarker()));
        googleMap.addMarker(new MarkerOptions().position(placeLocateResult.getActualLocation().getLatLng()).title("Actual Location").icon(BitmapDescriptorFactory.defaultMarker(Util.PRIMARY_COLOR_HUE)));


        LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(placeLocateResult.getGuessedLocation());
        builder.include(placeLocateResult.getActualLocation().getLatLng());
        LatLngBounds bounds = builder.build();

        cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, (int) (32 * getResources().getDisplayMetrics().density));
        googleMap.animateCamera(cameraUpdate);
        showLine(placeLocateResult);
    }

    public void showLine(PlaceLocateResult placeLocateResult){
        PolylineOptions connectingLine = new PolylineOptions()
                .add(placeLocateResult.getGuessedLocation())
                .add(placeLocateResult.getActualLocation().getLatLng())
                .color(Color.BLACK)
                .width(getResources().getDisplayMetrics().density * 5);
        googleMap.addPolyline(connectingLine);

        // draw circles to round out edges
        float radius = getResources().getDisplayMetrics().density * 5;
        googleMap.addCircle(new CircleOptions().center(placeLocateResult.getGuessedLocation()).radius(radius).fillColor(Color.BLACK).zIndex(10));
        googleMap.addCircle(new CircleOptions().center(placeLocateResult.getActualLocation().getLatLng()).radius(radius).fillColor(Color.BLACK).zIndex(10));
    }
}
