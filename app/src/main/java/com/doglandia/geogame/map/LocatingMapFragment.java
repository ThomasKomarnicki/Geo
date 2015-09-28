package com.doglandia.geogame.map;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doglandia.geogame.R;
import com.doglandia.geogame.activity.locate.PlaceLocateBaseActivity;
import com.doglandia.geogame.view.GuessHereInfoWindow;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocatingMapFragment extends Fragment implements GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener {

    private static final String ACTION_BUTTON_VISIBLE = "action_button_visible";
    private static final String MARKER_LOCATION = "marker_location";

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap googleMap;

    private LatLng selectedLocation;

//    private Marker marker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locate_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.locate_map_fragment);

        if(savedInstanceState != null) {

            if (savedInstanceState.containsKey(MARKER_LOCATION)) {
                selectedLocation = savedInstanceState.getParcelable(MARKER_LOCATION);

            }

        }

        mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LocatingMapFragment.this.googleMap = googleMap;
                googleMap.getUiSettings().setRotateGesturesEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setTiltGesturesEnabled(false);
                googleMap.setOnMapClickListener(LocatingMapFragment.this);
                googleMap.setOnMapLongClickListener(LocatingMapFragment.this);
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return true;
                    }
                });

                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return new GuessHereInfoWindow(getActivity());
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        return null;
                    }
                });
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        ((PlaceLocateBaseActivity) getActivity()).onLocationSelected(selectedLocation);
                    }
                });

                if (selectedLocation != null) {
                    onMapLocationClicked(selectedLocation);
                }

            }
        });

    }


    public void restoreControls() {
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.setOnMapClickListener(LocatingMapFragment.this);
        googleMap.setOnMapLongClickListener(LocatingMapFragment.this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        clickVibrate();
        onMapLocationClicked(latLng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        clickVibrate();
        onMapLocationClicked(latLng);
    }

    private void clickVibrate(){
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }

    private void onMapLocationClicked(LatLng latLng) {
        selectedLocation = latLng;
//        if(floatingActionButton.getVisibility() == FloatingActionButton.INVISIBLE){
//            animateFABIn();
//        }
        googleMap.clear();

        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_target_marker)));
        marker.showInfoWindow();
    }

    public void clearMap(){
        if(googleMap != null) {
//        floatingActionButton.setVisibility(View.INVISIBLE);
            Log.d("LocatingMapFragment", "map cleared");
//        if(marker != null) {
//            marker.remove();
//            marker.setVisible(false);
//        }
            googleMap.clear();
            CameraUpdate defaultMapLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 1);
            googleMap.moveCamera(defaultMapLocation);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putBoolean(ACTION_BUTTON_VISIBLE, floatingActionButton.getVisibility() == View.VISIBLE);
        if(selectedLocation != null) {
            outState.putParcelable(MARKER_LOCATION, selectedLocation);
        }

    }

}
