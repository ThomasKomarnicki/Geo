package com.doglandia.geogame.map;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.doglandia.geogame.R;
import com.doglandia.geogame.activity.PlaceLocateActivityNewUi;
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

    private FloatingActionButton floatingActionButton;

    private LatLng selectedLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locate_fragment,null);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.locate_fragment_action_button);
        floatingActionButton.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.locate_map_fragment);

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
                if(selectedLocation != null) {
                    onMapLocationClicked(selectedLocation);
                }

//                googleMap.getUiSettings().setAllGesturesEnabled(false);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFABOut();
                ((PlaceLocateActivityNewUi) getActivity()).onLocationSelected(selectedLocation);
            }
        });

    }

    private void animateFABOut(){
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                floatingActionButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(500);
        floatingActionButton.startAnimation(animation);

    }

    public void restoreControls(){
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.setOnMapClickListener(LocatingMapFragment.this);
        googleMap.setOnMapLongClickListener(LocatingMapFragment.this);
    }

    private void animateFABIn(){
        floatingActionButton.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_in);
        animation.setDuration(500);
        floatingActionButton.startAnimation(animation);

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

    private void onMapLocationClicked(LatLng latLng){
        selectedLocation = latLng;
        if(floatingActionButton.getVisibility() == FloatingActionButton.INVISIBLE){
            animateFABIn();
        }
        googleMap.clear();

        googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
    }

    public void clearMap(){
        floatingActionButton.setVisibility(View.INVISIBLE);
        googleMap.clear();
        CameraUpdate defaultMapLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(0,0),1);
        googleMap.moveCamera(defaultMapLocation);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ACTION_BUTTON_VISIBLE, floatingActionButton.getVisibility() == View.VISIBLE);
        if(selectedLocation != null) {
            outState.putParcelable(MARKER_LOCATION, selectedLocation);
        }

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {

            if (savedInstanceState.getBoolean(ACTION_BUTTON_VISIBLE, false)) {
                floatingActionButton.setVisibility(View.VISIBLE);
            }
            if (savedInstanceState.containsKey(MARKER_LOCATION)) {
                selectedLocation = savedInstanceState.getParcelable(MARKER_LOCATION);
//                if(googleMap != null) {
//                    onMapLocationClicked(selectedLocation); // this function expects to always have valid googleMap
//                    // shouldn't every get called?
//                }
            }

        }

    }

//    public void getSnapshot(final GoogleMap.SnapshotReadyCallback snapshotReadyCallback){
//        if(googleMap != null) {
//            googleMap.snapshot(snapshotReadyCallback);
//        }else{
////            this.snapshotReadyCallback = snapshotReadyCallback;
////            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
////                @Override
////                public void onMapReady(GoogleMap googleMap) {
////
////                }
////            });
//        }
//
//    }


}
