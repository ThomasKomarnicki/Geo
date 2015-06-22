package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.map.DiscoverMapFragment;
import com.doglandia.geogame.map.DiscoverStreetViewFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thomas on 6/21/2015.
 */
public class DiscoverActivity extends AppCompatActivity {

    private DiscoverMapFragment discoverMapFragment;

    private DiscoverStreetViewFragment discoverStreetViewFragment;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.discovery_activity);

        new NavigationAdapter(this);
        NavigationAdapter.setUpNavDrawerActivity(this,"Discover");

        toolbar =  (Toolbar)findViewById(R.id.recent_locations_toolbar);

        discoverMapFragment = (DiscoverMapFragment) getSupportFragmentManager().findFragmentById(R.id.discover_map_framgnet);

        discoverStreetViewFragment = (DiscoverStreetViewFragment) getSupportFragmentManager().findFragmentById(R.id.discover_steet_view_fragment);
        getSupportFragmentManager().beginTransaction().hide(discoverStreetViewFragment).commit();
    }

    public void onMapLocationClicked(LatLng latLng){
        discoverStreetViewFragment.setLocation(latLng);
        getSupportFragmentManager().beginTransaction().show(discoverStreetViewFragment).addToBackStack("discover_street_view_fragment").commit();

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
                NavigationAdapter.setUpNavDrawerActivity(DiscoverActivity.this,"Discover");
            }
        });

    }
}
