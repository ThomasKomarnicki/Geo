package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

        Server.getInstance().addUserLocation(place, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
        getSupportFragmentManager().popBackStack();
        NavigationAdapter.setUpNavDrawerActivity(DiscoverActivity.this,"Discover");
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
                NavigationAdapter.setUpNavDrawerActivity(DiscoverActivity.this,"Discover");
            }
        });
    }

    public void setDiscoverToolbar(){
        navigationAdapter.initNavigation();
        NavigationAdapter.setUpNavDrawerActivity(this,"Discover");
    }

    public void hideStreetViewFragment(){
        streetViewContainer.setVisibility(View.INVISIBLE);
        discoverStreetViewFragment.setVisible(false);
        setDiscoverToolbar();
    }

    public void showStreetViewFragment(){
        setStreetViewToolbar();
        streetViewContainer.setVisibility(View.VISIBLE);
        discoverStreetViewFragment.setVisible(true);
    }

    @Override
    public void onBackPressed() {
       if(streetViewContainer.getVisibility() == View.VISIBLE){
           // hide street view fragment
           hideStreetViewFragment();

       }else{
           super.onBackPressed();
       }
    }
}
