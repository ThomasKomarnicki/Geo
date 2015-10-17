package com.doglandia.unearthed.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.activity.OnHeatMapClickedListener;
import com.doglandia.unearthed.model.PlaceDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
                ((OnHeatMapClickedListener) getActivity()).onHeatMapClicked();
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
        int overlayRadius = (int) (getResources().getDisplayMetrics().density * 200);
        if(this.placeDetails.getLocationGuesses() != null && !this.placeDetails.getLocationGuesses().isEmpty()) {
//            HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
//            builder.data(this.placeDetails.getLocationGuesses());
//
//            HeatmapTileProvider heatmapTileProvider = builder.build();
//            googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider).fadeIn(true));
            int size = (int) (getResources().getDisplayMetrics().density * 10);
            Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.map_circle);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
            bitmap.recycle();

            for(LatLng latLng : placeDetails.getLocationGuesses()) {
//                googleMap.addCircle(new CircleOptions().center(latLng).radius(4000).fillColor(Color.BLACK).visible(true).zIndex(10));
//                googleMap.addGroundOverlay(new GroundOverlayOptions().position(latLng, 4000).visible(true).zIndex(10).image(BitmapDescriptorFactory.defaultMarker()));
                googleMap.addMarker(new MarkerOptions().position(latLng).visible(true).icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap)));
                Log.d("PlaceHeatMapFragment", "added heat tile");
            }
            googleMap.setOnMarkerClickListener(null);

        }
    }

    public void clearHeat() {
        if(googleMap != null) {
            googleMap.clear();
        }
    }
}
