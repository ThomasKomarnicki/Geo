package com.doglandia.geogame.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.map.DiscoverMapFragment;
import com.doglandia.geogame.map.DiscoverStreetViewFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.server.Server;
import com.doglandia.geogame.transmissions.TransmissionFragment;
import com.doglandia.geogame.transmissions.TransmissionTextListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DiscoverActivity extends CalligraphyActivity implements TransmissionTextListener {

    private DiscoverMapFragment discoverMapFragment;

    private DiscoverStreetViewFragment discoverStreetViewFragment;

    private Toolbar toolbar;

    private FrameLayout streetViewContainer;

    private NavigationAdapter navigationAdapter;

    private boolean showingStreetView = false;

    private boolean awaitingInitialStreetViewResult = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.discovery_activity);

        streetViewContainer = (FrameLayout) findViewById(R.id.discover_street_view_map_container);
        streetViewContainer.setVisibility(View.INVISIBLE);

        navigationAdapter = NavigationAdapter.setUpNavDrawerActivity(this,"Discover");

        toolbar =  (Toolbar)findViewById(R.id.recent_locations_toolbar);

        discoverMapFragment = (DiscoverMapFragment) getSupportFragmentManager().findFragmentById(R.id.discover_map_framgnet);

        discoverStreetViewFragment = (DiscoverStreetViewFragment) getSupportFragmentManager().findFragmentById(R.id.discover_steet_view_fragment);
        discoverStreetViewFragment.setVisible(false);

        if(UserAuth.isDiscoverFirstRun(this)){
            TransmissionFragment transmissionFragment = TransmissionFragment.createInstance(1000,"Here you can select locations");
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content,transmissionFragment,"transmission_fragment")
                    .commitAllowingStateLoss();

            transmissionFragment.setTransmissionEndedTextListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TransmissionFragment transmissionFragment = (TransmissionFragment) getSupportFragmentManager().findFragmentByTag("transmission_fragment");
        if(transmissionFragment != null) {
            transmissionFragment.setTransmissionEndedTextListener(this);
        }
    }

    public void onMapLocationClicked(LatLng latLng){

        Log.d(this.getLocalClassName(), "on map location clicked " + latLng);
        getSupportFragmentManager().beginTransaction()
                .show(discoverStreetViewFragment)
                .commit();

        awaitingInitialStreetViewResult = true; // set this to differentiate between click and street view navigation
        discoverStreetViewFragment.setLocation(latLng);
    }

    public void onLocationAdded(LatLng latLng){
        Place place = new Place();
        place.setLatLng(latLng);
        place.setUserId(UserAuth.getAuthUserId());

        Server.getInstance().addUserLocation(place, UserAuth.getAuthUserToken(), new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
                Snackbar.make(DiscoverActivity.this.findViewById(R.id.discover_activity_coordinator),"Location Added",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
        hideStreetViewFragment();
        NavigationAdapter.setUpNavDrawerActivity(DiscoverActivity.this, "Discover");
        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().popBackStack("discover_street_view_fragment",0);
        discoverMapFragment.resetMapZoom();

//        super.onBackPressed(); // don't know why this is needed, maybe back stack isn't cleared?
    }

    public void onLocationChangeResult(StreetViewPanoramaLocation streetViewPanoramaLocation){
        if(streetViewPanoramaLocation == null){
            showNoLocationToast();
        }else if(awaitingInitialStreetViewResult){
            showStreetViewFragment();
            awaitingInitialStreetViewResult = false;
        }
    }

    private void showNoLocationToast(){
        Toast.makeText(this,"Street View Not Found",Toast.LENGTH_SHORT).show();
    }

    public void setStreetViewToolbar(){
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getSupportFragmentManager().popBackStack();
                onBackPressed();
                NavigationAdapter.setUpNavDrawerActivity(DiscoverActivity.this, "Discover");
            }
        });
    }

    public void setDiscoverToolbar(){
        navigationAdapter.initNavigation();
        NavigationAdapter.setUpNavDrawerActivity(this, "Discover");
    }

    public void hideStreetViewFragment(){
        hideStreetViewAnimation();
        setDiscoverToolbar();
        showingStreetView = false;
    }

    public void showStreetViewFragment(){
        showingStreetView = true;
        setStreetViewToolbar();
        showStreetViewAnimation();
    }

    private void hideStreetViewAnimation(){

        if(Build.VERSION.SDK_INT >=21) {
            int cx = (streetViewContainer.getLeft() + streetViewContainer.getRight()) / 2;
            int cy = (streetViewContainer.getTop() + streetViewContainer.getBottom()) / 2;

            int initialRadius = streetViewContainer.getWidth();

            Animator anim = ViewAnimationUtils.createCircularReveal(streetViewContainer, cx, cy, initialRadius, 0);
            anim.setDuration(500);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    streetViewContainer.setVisibility(View.INVISIBLE);
                    discoverStreetViewFragment.setVisible(false);
                }
            });
            anim.start();
        }else{
            // pre 5.1
            streetViewContainer.setVisibility(View.INVISIBLE);
            discoverStreetViewFragment.setVisible(false);
        }
    }

    private void showStreetViewAnimation(){
        if(Build.VERSION.SDK_INT >=21) {
            int cx = (streetViewContainer.getLeft() + streetViewContainer.getRight()) / 2;
            int cy = (streetViewContainer.getTop() + streetViewContainer.getBottom()) / 2;

            int finalRadius = Math.max(streetViewContainer.getWidth(), streetViewContainer.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(streetViewContainer, cx, cy, 0, finalRadius);
            anim.setDuration(500);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    streetViewContainer.setVisibility(View.VISIBLE);
                    discoverStreetViewFragment.setVisible(true);
                }
            });

            streetViewContainer.setVisibility(View.VISIBLE);
            anim.start();
        }else{
            // pre 5.1
            streetViewContainer.setVisibility(View.VISIBLE);
            discoverStreetViewFragment.setVisible(true);
        }
    }

    @Override
    public void onBackPressed() {
        if(!navigationAdapter.onBackPressed()) {
            if (showingStreetView) {
                // hide street view fragment
                hideStreetViewFragment();

            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onTransmissionEnd() {
        UserAuth.finishedDiscoverFirstRun(this);
    }

    @Override
    public void onNewTextShown(int textIndex) {

    }
}
