package com.doglandia.geogame.activity;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.HeatDialogMapFragment;
import com.doglandia.geogame.fragment.MyPlacesFragment;
import com.doglandia.geogame.fragment.PlaceDetailsFragment;
import com.doglandia.geogame.fragment.error.ButtonLessNoDataFragment;
import com.doglandia.geogame.fragment.error.NoPlacesFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.server.GeoCodeTask;
import com.doglandia.geogame.server.Server;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyPlacesActivity extends AppCompatActivity implements OnHeatMapClickedListener{

    private static final String CURRENTLY_SELECTED_PLACE ="currently_selected_place";
    private static final String PLACES = "places";
    private static final String TAG = "MyPlacesActivity";

    boolean showTwoPane = false;
    private boolean showingHeatMap = false;

    private PlaceDetailsFragment placeDetailsFragment;

    private MyPlacesFragment myPlacesFragment;

    private int currentlySelectedPlace = 0;

    private ArrayList<Place> places;

    private FrameLayout contentHolder;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_places_activity);

        contentHolder = (FrameLayout) findViewById(R.id.my_places_content);
        progressBar = (ProgressBar) findViewById(R.id.my_places_progress);

        setNavDrawerToolbar();

        showTwoPane = getResources().getBoolean(R.bool.show_two_pane_layout);

        if(showTwoPane){
            placeDetailsFragment = (PlaceDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.place_details_fragment);
            getSupportFragmentManager().beginTransaction().hide(placeDetailsFragment).commit();
        }

        myPlacesFragment = (MyPlacesFragment) getSupportFragmentManager().findFragmentById(R.id.my_places_fragment);

        if(savedInstanceState != null && savedInstanceState.containsKey(PLACES)){
            currentlySelectedPlace = savedInstanceState.getInt(CURRENTLY_SELECTED_PLACE,-1);
            places = Parcels.unwrap(savedInstanceState.getParcelable(PLACES));
        }

        if(places == null) {
            getPlacesFromServer(1);
        }else{
            if (places.size() == 0) {
                showNoPlacesMessage();
            } else if (places != null) {
                progressBar.setVisibility(View.GONE);
                MyPlacesActivity.this.places = places;
//                Log.d(getLocalClassName(), "got " + places.size() + " places");
                myPlacesFragment.showPlacesList(places);
                if(showTwoPane) {
                    myPlacesFragment.onPlaceClick(places.get(currentlySelectedPlace), currentlySelectedPlace);
                    myPlacesFragment.highlightSelected();
                }
            }
        }
    }

    public void getPlacesFromServer(int page){
        Log.d(TAG,"places == null, fetching data");
        Server.getInstance().getUserLocations(UserAuth.getAuthUserId(),page, new Callback<ArrayList<Place>>() {
            @Override
            public void success(ArrayList<Place> retrievedPlaces, Response response) {

                if (places == null && (retrievedPlaces == null || retrievedPlaces.size() == 0)) {
                    showNoPlacesMessage();
                    MyPlacesActivity.this.places = new ArrayList<>();
                } else if (retrievedPlaces != null) {
//                        Geocoder geocoder = new Geocoder(MyPlacesActivity.this, Locale.getDefault());
//                        for(Place place : places){
//                            place.geocode(geocoder);
//                        }

                    if(places == null) {
                        places = retrievedPlaces;
                    }else{
                        places.addAll(retrievedPlaces);
                    }
                    GeoCodeTask geoCodeTask = new GeoCodeTask(MyPlacesActivity.this){
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            progressBar.setVisibility(View.GONE);

                            Log.d(getLocalClassName(), "got " + places.size() + " places");
                            myPlacesFragment.showPlacesList(places);
                            if(showTwoPane) {
                                myPlacesFragment.onPlaceClick(places.get(0), 0);
                                myPlacesFragment.highlightSelected();
                            }
                        }
                    };
                    Place[] placesArray = new Place[retrievedPlaces.size()];
                    geoCodeTask.execute(retrievedPlaces.toArray(placesArray));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);


                if(places == null){
                    // TODO show could not connect to server
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.error_content, new ButtonLessNoDataFragment())
                            .commit();
                }
            }
        });
    }

    private void showNoPlacesMessage(){
        // remove all content views
        // show fragment

        contentHolder.removeAllViews();
        getSupportFragmentManager().beginTransaction().remove(myPlacesFragment).commit();
        if(placeDetailsFragment != null){
            getSupportFragmentManager().beginTransaction().remove(placeDetailsFragment).commit();
        }
        getSupportFragmentManager().beginTransaction().add(contentHolder.getId(),new NoPlacesFragment(),"no_places_fragment").commit();
    }

    public void onPlaceClick(Place place, int position){
        currentlySelectedPlace = position;
        if(showTwoPane){
            getSupportFragmentManager().beginTransaction().show(placeDetailsFragment).commit();
            placeDetailsFragment.getPlaceDetails(place);
        }else{
            Intent intent = new Intent(this,PlaceDetailsActivity.class);
            intent.putExtra("place", Parcels.wrap(place));
            startActivity(intent);
        }
    }

    @Override
    public boolean onHeatMapClicked() {
        setHeatMapToolbar();
        showingHeatMap = true;
        Log.d(TAG,"my places heat map clicked");
        HeatDialogMapFragment heatDialogMapFragment = new HeatDialogMapFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.heat_map_holder,heatDialogMapFragment,"heat_dialog_map_fragment")
                .addToBackStack("heat_dialog_map_fragment")
                .commit();

        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENTLY_SELECTED_PLACE,currentlySelectedPlace);
        outState.putParcelable(PLACES, Parcels.wrap(places));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            currentlySelectedPlace = savedInstanceState.getInt(CURRENTLY_SELECTED_PLACE,-1);
            places = Parcels.unwrap(savedInstanceState.getParcelable(PLACES));
        }
    }

    private void setHeatMapToolbar(){
        // todo
        Toolbar toolbar = (Toolbar) findViewById(R.id.recent_locations_toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle("Guessed Places");
    }

    private void setNavDrawerToolbar(){
        new NavigationAdapter(this);
        NavigationAdapter.setUpNavDrawerActivity(this,"My Places");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(showingHeatMap){
            setNavDrawerToolbar();
        }
    }

    public PlaceDetailsFragment getPlaceDetailsFragment() {
        return placeDetailsFragment;
    }
}
