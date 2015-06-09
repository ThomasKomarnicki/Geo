package com.doglandia.geogame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.PlaceLocatePaterAdapter;
import com.doglandia.geogame.map.LocatingMapFragment;
import com.doglandia.geogame.map.StreetViewMapFragment;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.doglandia.geogame.navigation.NavigationItemManager;
import com.doglandia.server.Server;
import com.doglandia.server.model.Place;
import com.doglandia.server.model.PlaceLocateResult;
import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcels;

public class PlaceLocateActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private static final int START_NEW_LOCATION_RESULT = 0x420;

    private TabLayout mTabLayout;
    private NavigationView mNavigationView;
    private DrawerLayout mNavDrawer;
    private Toolbar mToolbar;

    private ViewPager mViewPager;
    private PlaceLocatePaterAdapter mPlaceLocatePaterAdapter;

    private Place place;

    private StreetViewMapFragment streetViewMapFragment;
    private LocatingMapFragment locatingMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(streetViewMapFragment == null){
            streetViewMapFragment = new StreetViewMapFragment();
        }
        if(locatingMapFragment == null){
            locatingMapFragment = new LocatingMapFragment();
        }

        mPlaceLocatePaterAdapter = new PlaceLocatePaterAdapter(getSupportFragmentManager());
        mPlaceLocatePaterAdapter.setLocatingMapFragment(locatingMapFragment);
        mPlaceLocatePaterAdapter.setStreetViewMapFragment(streetViewMapFragment);

        mNavigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationItemManager(this));

        mNavDrawer = (DrawerLayout) findViewById(R.id.main_nav_drawer);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mViewPager = (ViewPager) findViewById(R.id.main_pager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);


        mViewPager.setAdapter(mPlaceLocatePaterAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText("Explore"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Locate"));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setOnTabSelectedListener(this);

        mToolbar.setTitle("GeoGame");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavDrawer.isDrawerOpen(GravityCompat.START)) {
                    mNavDrawer.closeDrawers();
                } else {
                    mNavDrawer.openDrawer(GravityCompat.START);
                }
            }
        });
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        setSupportActionBar(mToolbar);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        initiateTestData();
    }

    private void initiateTestData(){
        Server.getInstance().getNewLocation(0, new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
                PlaceLocateActivity.this.place = place;
                streetViewMapFragment.setPosition(place.getLatLng());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition(),true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void onLocationSelected(LatLng latLng){
        Intent intent = new Intent(this,LocatePlaceResultsActivity.class);
        PlaceLocateResult placeLocateResult = new PlaceLocateResult();
        placeLocateResult.setGuessedLocation(latLng);
        placeLocateResult.setActualLocation(place);
        intent.putExtra("locate_result", Parcels.wrap(placeLocateResult));
        startActivityForResult(intent, START_NEW_LOCATION_RESULT);

        Server.getInstance().locationGuess(0, placeLocateResult, new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
                PlaceLocateActivity.this.place = place;
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == START_NEW_LOCATION_RESULT){
            setNewPlace();
        }
    }

    private void setNewPlace(){
        streetViewMapFragment.setPosition(place.getLatLng());
        mViewPager.setCurrentItem(0);
    }
}