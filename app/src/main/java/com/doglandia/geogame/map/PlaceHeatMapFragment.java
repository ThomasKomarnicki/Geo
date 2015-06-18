package com.doglandia.geogame.map;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.List;

/**
 * Created by Thomas on 6/15/2015.
 */
public class PlaceHeatMapFragment extends SupportMapFragment {

    GoogleMap googleMap;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                PlaceHeatMapFragment.this.googleMap = googleMap;
            }
        });
    }


    public void showHeat(List<LatLng> list){
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(list);

//        int[] colors;
//        float[] points;
//        Gradient gradient = new Gradient(colors,points);
//        builder.gradient(gradient);

        HeatmapTileProvider heatmapTileProvider = builder.build();
        TileOverlay overlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider).fadeIn(true));

    }
}
