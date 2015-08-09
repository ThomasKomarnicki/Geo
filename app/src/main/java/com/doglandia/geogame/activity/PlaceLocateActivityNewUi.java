package com.doglandia.geogame.activity;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.PlaceLocateControllerFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.server.Server;
import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 7/23/2015.
 */
public class PlaceLocateActivityNewUi extends AppCompatActivity {

    private static final int START_NEW_LOCATION_RESULT = 0x420;

    private NavigationView mNavigationView;
    private DrawerLayout mNavDrawer;
    private Toolbar mToolbar;

    private PlaceLocateControllerFragment placeLocateControllerFragment;

    private boolean backNavigation = false;

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_locate_new_ui);

        UserAuth.spinUp(this);

        mNavDrawer = (DrawerLayout) findViewById(R.id.main_nav_drawer);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);

        setSupportActionBar(mToolbar);

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
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);


        new NavigationAdapter(this);

        placeLocateControllerFragment = (PlaceLocateControllerFragment) getSupportFragmentManager().findFragmentById(R.id.place_locate_controller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(place == null){
            showUserCurrentLocation();
        }
    }

    @Override
    public void onBackPressed() {
        if(backNavigation && placeLocateControllerFragment.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

    public void setBackNavigation(){
        backNavigation = true;
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeLocateControllerFragment.onBackPressed();
            }
        });
    }
    public void setMenuNavigation(){
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
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
    }

    private void showUserCurrentLocation(){
        Server.getInstance().getLocation(UserAuth.getAuthUserCurrentLocation(), new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
//                currentLocationManager.onNewLocationRetrieved(place);
                placeLocateControllerFragment.setPosition(place.getLatLng());
                PlaceLocateActivityNewUi.this.place = place;
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    public void onLocationSelected(LatLng latLng){
        Intent intent = new Intent(this,LocatePlaceResultsActivity.class);
        PlaceLocateResult placeLocateResult = new PlaceLocateResult(UserAuth.getAuthUserId());
        placeLocateResult.setGuessedLocation(latLng);
        placeLocateResult.setActualLocation(place);
        place.geocode(new Geocoder(this));
        intent.putExtra("locate_result", Parcels.wrap(placeLocateResult));
        startActivityForResult(intent, START_NEW_LOCATION_RESULT);

        Server.getInstance().postLocateResult(placeLocateResult, new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
                PlaceLocateActivityNewUi.this.place = place;
                UserAuth.setCurrentLocation(place.getId(),PlaceLocateActivityNewUi.this);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == START_NEW_LOCATION_RESULT){
            placeLocateControllerFragment.reset();
            setNewPlace();
        }
    }

    private void setNewPlace(){
        placeLocateControllerFragment.setPosition(place.getLatLng());
        placeLocateControllerFragment.clearMap();
    }
}
