package com.doglandia.geogame.activity.locate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.activity.LocatePlaceResultsActivity;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.CalculatingLocationResultsFragment;
import com.doglandia.geogame.fragment.PlaceLocateControllerFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.model.response.LocationGuessResult;
import com.doglandia.geogame.server.GeoCodeTask;
import com.doglandia.geogame.server.Server;
import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 8/16/2015.
 */
public class PlaceLocateBaseActivity extends AppCompatActivity {

    private static final int START_NEW_LOCATION_RESULT = 0x420;

    private DrawerLayout mNavDrawer;
    private ImageView menuIcon;

    private PlaceLocateControllerFragment placeLocateControllerFragment;

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_locate_base);

        UserAuth.spinUp(this);

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

        new NavigationAdapter(this);

        placeLocateControllerFragment = (PlaceLocateControllerFragment) getSupportFragmentManager().findFragmentById(R.id.place_locate_controller);
    }

    protected void fetchUserCurrentLocation(final boolean showOnCompletion){
        Server.getInstance().getLocation(UserAuth.getAuthUserCurrentLocation(), new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
                PlaceLocateBaseActivity.this.place = place;
//                placeLocateControllerFragment.setPosition(place.getLatLng());
                if(showOnCompletion){
                    placeLocateControllerFragment.setPosition(place.getLatLng());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
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
                UserAuth.setCurrentLocation(place.getId(), PlaceLocateBaseActivity.this);

                new GeoCodeTask(PlaceLocateBaseActivity.this) {
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        calculatingLocationResultsFragment.dismissAllowingStateLoss();
                        Intent intent = new Intent(PlaceLocateBaseActivity.this, LocatePlaceResultsActivity.class);

//                      place.geocode(new Geocoder(PlaceLocateBaseActivity.this));
                        intent.putExtra("locate_result", Parcels.wrap(locationGuessResult.getLocationGuessResult()));
                        startActivityForResult(intent, START_NEW_LOCATION_RESULT);

                        placeLocateControllerFragment.reset();
                        placeLocateControllerFragment.setPosition(place.getLatLng());

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
    }

    private void setNewPlace(){
        placeLocateControllerFragment.setPosition(place.getLatLng());
        placeLocateControllerFragment.clearMap();
    }
}
