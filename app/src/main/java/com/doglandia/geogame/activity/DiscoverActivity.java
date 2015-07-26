package com.doglandia.geogame.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/21/2015.
 */
public class DiscoverActivity extends AppCompatActivity {

    private DiscoverMapFragment discoverMapFragment;

    private DiscoverStreetViewFragment discoverStreetViewFragment;

    private Toolbar toolbar;

    private FrameLayout streetViewContainer;

    private NavigationAdapter navigationAdapter;

    private boolean showingStreetView = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.discovery_activity);

        streetViewContainer = (FrameLayout) findViewById(R.id.discover_street_view_map_container);
        streetViewContainer.setVisibility(View.INVISIBLE);

        navigationAdapter = new NavigationAdapter(this);
        NavigationAdapter.setUpNavDrawerActivity(this,"Discover");

        toolbar =  (Toolbar)findViewById(R.id.recent_locations_toolbar);

        discoverMapFragment = (DiscoverMapFragment) getSupportFragmentManager().findFragmentById(R.id.discover_map_framgnet);

        discoverStreetViewFragment = (DiscoverStreetViewFragment) getSupportFragmentManager().findFragmentById(R.id.discover_steet_view_fragment);
        discoverStreetViewFragment.setVisible(false);
//        getSupportFragmentManager().beginTransaction().hide(discoverStreetViewFragment).commit();
    }

    public void onMapLocationClicked(LatLng latLng){
        getSupportFragmentManager().beginTransaction().show(discoverStreetViewFragment).addToBackStack("discover_street_view_fragment").commit();
        Log.d(this.getLocalClassName(),"on map location clicked "+latLng);
        getSupportFragmentManager().beginTransaction().show(discoverStreetViewFragment).addToBackStack("discover_street_view_fragment").commit();
        discoverStreetViewFragment.setLocation(latLng);
    }

    public void onLocationAdded(LatLng latLng){
        Place place = new Place();
        place.setLatLng(latLng);
        place.setUserId(UserAuth.getAuthUserId());

        Server.getInstance().addUserLocation(place, new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
                // TODO zoom out of location // reset map camera
                // TODO limit places around previously selected places
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
        getSupportFragmentManager().popBackStack();
        NavigationAdapter.setUpNavDrawerActivity(DiscoverActivity.this, "Discover");

        discoverMapFragment.resetMapZoom();
    }

    public void onLocationChangeResult(StreetViewPanoramaLocation streetViewPanoramaLocation){
        if(streetViewPanoramaLocation == null){
            showNoLocationToast();
//            getSupportFragmentManager().beginTransaction().hide(discoverStreetViewFragment).commit();
        }else{
           showStreetViewFragment();
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
                getSupportFragmentManager().popBackStack();
                NavigationAdapter.setUpNavDrawerActivity(DiscoverActivity.this, "Discover");
            }
        });
    }

    public void setDiscoverToolbar(){
        navigationAdapter.initNavigation();
        NavigationAdapter.setUpNavDrawerActivity(this, "Discover");
    }

    public void hideStreetViewFragment(){
//        streetViewContainer.setVisibility(View.INVISIBLE);
//        discoverStreetViewFragment.setVisible(false);
        hideStreetViewAnimation();
        setDiscoverToolbar();
        showingStreetView = false;
    }

    public void showStreetViewFragment(){
        showingStreetView = true;
        setStreetViewToolbar();
//        streetViewContainer.setVisibility(View.VISIBLE);
//        discoverStreetViewFragment.setVisible(true);
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
        }
    }

    @Override
    public void onBackPressed() {
       if(showingStreetView){
           // hide street view fragment
           hideStreetViewFragment();

       }else{
           super.onBackPressed();
       }
    }
}
