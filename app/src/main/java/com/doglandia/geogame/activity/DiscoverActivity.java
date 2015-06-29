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
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.map.DiscoverMapFragment;
import com.doglandia.geogame.map.DiscoverStreetViewFragment;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.discovery_activity);

        streetViewContainer = (FrameLayout) findViewById(R.id.discover_street_view_map_container);
        streetViewContainer.setVisibility(View.INVISIBLE);

        new NavigationAdapter(this);
        NavigationAdapter.setUpNavDrawerActivity(this,"Discover");

        toolbar =  (Toolbar)findViewById(R.id.recent_locations_toolbar);

        discoverMapFragment = (DiscoverMapFragment) getSupportFragmentManager().findFragmentById(R.id.discover_map_framgnet);

        discoverStreetViewFragment = (DiscoverStreetViewFragment) getSupportFragmentManager().findFragmentById(R.id.discover_steet_view_fragment);
//        getSupportFragmentManager().beginTransaction().hide(discoverStreetViewFragment).commit();
    }

    public void onMapLocationClicked(LatLng latLng){
        getSupportFragmentManager().beginTransaction().show(discoverStreetViewFragment).addToBackStack("discover_street_view_fragment").commit();
        Log.d(this.getLocalClassName(),"on map location clicked "+latLng);
        getSupportFragmentManager().beginTransaction().show(discoverStreetViewFragment).addToBackStack("discover_street_view_fragment").commit();
        discoverStreetViewFragment.setLocation(latLng);
    }

    public void onLocationAdded(LatLng latLng){
        Server.getInstance().addUserLocation(0, latLng, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

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
            streetViewContainer.setVisibility(View.VISIBLE);
//            // todo go to street view
//            getSupportFragmentManager().beginTransaction().show(discoverStreetViewFragment).addToBackStack("discover_street_view_fragment").commit();
////            streetViewContainer.setVisibility(View.VISIBLE);
//
//            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getSupportFragmentManager().popBackStack();
//                    NavigationAdapter.setUpNavDrawerActivity(DiscoverActivity.this,"Discover");
//                }
//            });


        }
    }

    private void showNoLocationToast(){
        Toast.makeText(this,"Street View Not Found",Toast.LENGTH_SHORT).show();
    }
}
