package com.doglandia.unearthed.activity.locate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.UserAuth;
import com.doglandia.unearthed.activity.CalligraphyActivity;
import com.doglandia.unearthed.activity.LocatePlaceResultsActivity;
import com.doglandia.unearthed.adapter.NavigationAdapter;
import com.doglandia.unearthed.fragment.CalculatingLocationResultsFragment;
import com.doglandia.unearthed.fragment.PlaceLocateControllerFragment;
import com.doglandia.unearthed.model.Place;
import com.doglandia.unearthed.model.PlaceLocateResult;
import com.doglandia.unearthed.model.response.LocationGuessResult;
import com.doglandia.unearthed.server.GeoCodeTask;
import com.doglandia.unearthed.server.Server;
import com.doglandia.unearthed.util.CurrentLocationManager;
import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 8/16/2015.
 */
public abstract class PlaceLocateBaseActivity extends CalligraphyActivity {

    private static final int START_NEW_LOCATION_RESULT = 0x420;

    private DrawerLayout mNavDrawer;
    private ImageView menuIcon;

    private PlaceLocateControllerFragment placeLocateControllerFragment;

    private Place place;

    private NavigationAdapter navigationAdapter;

    private CurrentLocationManager currentLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_locate_base);

        UserAuth.spinUp(this);
        currentLocationManager = new CurrentLocationManager(this);
        currentLocationManager.setCurrentPlaceId(UserAuth.getAuthUserCurrentLocation());

        mNavDrawer = (DrawerLayout) findViewById(R.id.main_nav_drawer);
        menuIcon = (ImageView) findViewById(R.id.place_locate_menu);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavDrawer.isDrawerOpen(GravityCompat.START)) {
                    mNavDrawer.closeDrawers();
                } else {
                    mNavDrawer.openDrawer(GravityCompat.START);
                }
            }
        });

        navigationAdapter = new NavigationAdapter(this);

        placeLocateControllerFragment = (PlaceLocateControllerFragment) getSupportFragmentManager().findFragmentById(R.id.place_locate_controller);
    }

    protected void fetchUserCurrentLocation(final boolean showOnCompletion){
        if(currentLocationManager.getCurrentPlace() != null){
            if(showOnCompletion){
                PlaceLocateBaseActivity.this.place = currentLocationManager.getCurrentPlace();
                placeLocateControllerFragment.setPosition(currentLocationManager.getCurrentPlace().getLatLng());
            }
        }else {
            currentLocationManager.fetchLocation(new Callback<Place>() {
                @Override
                public void success(Place place, Response response) {
                    PlaceLocateBaseActivity.this.place = place;
//                placeLocateControllerFragment.setPosition(place.getLatLng());
                    if (showOnCompletion) {
                        placeLocateControllerFragment.setPosition(place.getLatLng());
                    }

                }

                @Override
                public void failure(RetrofitError error) {}
            });

        }
    }

    public void onLocationSelected(LatLng latLng){

        final CalculatingLocationResultsFragment calculatingLocationResultsFragment = new CalculatingLocationResultsFragment();
        calculatingLocationResultsFragment.show(getSupportFragmentManager(), "calculating_location_results_fragment");

        final PlaceLocateResult placeLocateResult = new PlaceLocateResult(UserAuth.getAuthUserId());
        placeLocateResult.setGuessedLocation(latLng);
        placeLocateResult.setActualLocation(place);


        Server.getInstance().postLocateResult(placeLocateResult, UserAuth.getAuthUserToken(), new Callback<LocationGuessResult>() {
            @Override
            public void success(final LocationGuessResult locationGuessResult, Response response) {
                PlaceLocateBaseActivity.this.place = locationGuessResult.getNewLocation();

                new GeoCodeTask(PlaceLocateBaseActivity.this) {
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        currentLocationManager.setCurrentPlace(place);
                        UserAuth.setCurrentLocation(place.getId(), PlaceLocateBaseActivity.this);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                placeLocateControllerFragment.reset();
                                placeLocateControllerFragment.setPosition(place.getLatLng());
                            }
                        },500);


                        if (!isChangingConfigurations()) {
                            calculatingLocationResultsFragment.dismissAllowingStateLoss();
                        }
                        Intent intent = new Intent(PlaceLocateBaseActivity.this, LocatePlaceResultsActivity.class);

//                      place.geocode(new Geocoder(PlaceLocateBaseActivity.this));
                        intent.putExtra("locate_result", Parcels.wrap(locationGuessResult.getLocationGuessResult()));
                        startActivityForResult(intent, START_NEW_LOCATION_RESULT);

                    }
                }.execute(locationGuessResult.getLocationGuessResult().getActualLocation());

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
//        if(requestCode == START_NEW_LOCATION_RESULT){
//            placeLocateControllerFragment.reset();
//            setNewPlace();
//        }
        placeLocateControllerFragment.reset();
        CalculatingLocationResultsFragment fragment = (CalculatingLocationResultsFragment) getSupportFragmentManager().findFragmentByTag("calculating_location_results_fragment");
        if(fragment != null){
            fragment.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        if(!navigationAdapter.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private void setNewPlace(){
        placeLocateControllerFragment.setPosition(place.getLatLng());
        placeLocateControllerFragment.clearMap();
    }

}
