package com.doglandia.geogame.map;

import android.os.Bundle;

import com.doglandia.geogame.activity.OnHeatMapClickedListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.List;

/**
 * Created by Thomas on 6/15/2015.
 */
public class PlaceHeatMapFragment extends SupportMapFragment {

    private GoogleMap googleMap;
    private List<LatLng> otherGuesses;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                PlaceHeatMapFragment.this.googleMap = googleMap;
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 1));
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        ((OnHeatMapClickedListener)getActivity()).onHeatMapClicked();
                    }
                });
                showHeat(otherGuesses);
            }
        });
    }


    public void showHeat(List<LatLng> list){
        if(list == null){
            list = otherGuesses;
        }else{
            otherGuesses = list;
        }
        if(list == null || googleMap == null){
            return;
        }
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(list);

        HeatmapTileProvider heatmapTileProvider = builder.build();
        TileOverlay overlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider).fadeIn(true));
    }
}
