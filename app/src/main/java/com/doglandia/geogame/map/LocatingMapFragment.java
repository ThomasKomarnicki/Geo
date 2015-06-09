package com.doglandia.geogame.map;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.doglandia.geogame.R;
import com.doglandia.geogame.activity.PlaceLocateActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Thomas on 6/3/2015.
 */
public class LocatingMapFragment extends Fragment implements GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener {

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;

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
                LocatingMapFragment.this.mGoogleMap = googleMap;
                googleMap.getUiSettings().setRotateGesturesEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setTiltGesturesEnabled(false);
                googleMap.setOnMapClickListener(LocatingMapFragment.this);
                googleMap.setOnMapLongClickListener(LocatingMapFragment.this);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFABOut();
                ((PlaceLocateActivity)getActivity()).onLocationSelected(selectedLocation);
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

    private void animateFABIn(){
        floatingActionButton.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_in);
        animation.setDuration(500);
        floatingActionButton.startAnimation(animation);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        onMapLocationClicked(latLng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        onMapLocationClicked(latLng);
    }

    private void onMapLocationClicked(LatLng latLng){
        selectedLocation = latLng;
        if(floatingActionButton.getVisibility() == floatingActionButton.INVISIBLE){
            animateFABIn();
        }
        mGoogleMap.clear();

        mGoogleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }
}
