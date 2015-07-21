package com.doglandia.geogame.map;

import android.os.Bundle;
import android.util.Log;

import com.doglandia.geogame.activity.OnHeatMapClickedListener;
import com.doglandia.geogame.model.PlaceDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

/**
 * Created by Thomas on 6/15/2015.
 */
public class PlaceHeatMapFragment extends SupportMapFragment {

    private GoogleMap googleMap;
    protected PlaceDetails placeDetails;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                PlaceHeatMapFragment.this.googleMap = googleMap;
                configMapSettings(googleMap);
                showHeat(placeDetails);
            }
        });
    }

    public void configMapSettings(GoogleMap googleMap){
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 1));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                ((OnHeatMapClickedListener)getActivity()).onHeatMapClicked();
            }
        });
        Log.d(getTag(), "configMapSettings showHeat");

    }

    public void showHeat(PlaceDetails placeDetails){
        if(placeDetails != null){
            this.placeDetails = placeDetails;
        }

        if(this.placeDetails == null || googleMap == null){
            return;
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(this.placeDetails.getPlace().getLatLng()));

        if(this.placeDetails.getLocationGuesses() != null && !this.placeDetails.getLocationGuesses().isEmpty()) {
            HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
            builder.data(this.placeDetails.getLocationGuesses());

            HeatmapTileProvider heatmapTileProvider = builder.build();
            TileOverlay overlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider).fadeIn(true));
            Log.d("PlaceHeatMapFragment", "added heat tiles");
        }
    }
}
