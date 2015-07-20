package com.doglandia.geogame.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.MyPlacesFragment;
import com.doglandia.geogame.fragment.PlaceDetailsFragment;
import com.doglandia.geogame.fragment.error.NoPlacesFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.server.Server;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/13/2015.
 */
public class MyPlacesActivity extends AppCompatActivity implements OnHeatMapClickedListener{

    private static final String CURRENTLY_SELECTED_PLACE ="currently_selected_place";
    private static final String PLACES = "places";

    boolean landscape = false;

    private PlaceDetailsFragment placeDetailsFragment;

    private MyPlacesFragment myPlacesFragment;

    private int currentlySelectedPlace = -1;

    private ArrayList<Place> places;

    private FrameLayout contentHolder;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_places_activity);

        contentHolder = (FrameLayout) findViewById(R.id.my_places_content);
        progressBar = (ProgressBar) findViewById(R.id.my_places_progress);

        new NavigationAdapter(this);
        NavigationAdapter.setUpNavDrawerActivity(this,"My Places");

        landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if(landscape){
            placeDetailsFragment = (PlaceDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.place_details_fragment);
        }

        myPlacesFragment = (MyPlacesFragment) getSupportFragmentManager().findFragmentById(R.id.my_places_fragment);

        if(savedInstanceState != null && savedInstanceState.containsKey(PLACES)){
            currentlySelectedPlace = savedInstanceState.getInt(CURRENTLY_SELECTED_PLACE,-1);
            places = Parcels.unwrap(savedInstanceState.getParcelable(PLACES));
        }

        if(places == null) {
            Server.getInstance().getUserLocations(UserAuth.getAuthUserId(), new Callback<ArrayList<Place>>() {
                @Override
                public void success(ArrayList<Place> places, Response response) {
                    progressBar.setVisibility(View.GONE);
                    MyPlacesActivity.this.places = places;
                    Log.d(getLocalClassName(), "got " + places.size() + " places");
                    if (places == null || places.size() == 0) {
                        showNoPlacesMessage();
                    } else if (places != null) {
                        myPlacesFragment.showPlacesList(places);
                        myPlacesFragment.onPlaceClick(places.get(0),0);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
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
        if(landscape){
            placeDetailsFragment.getPlaceDetails(place);
        }else{
            Intent intent = new Intent(this,PlaceDetailsActivity.class);
            intent.putExtra("place", Parcels.wrap(place));
            startActivity(intent);
        }
    }

    public boolean isLandscape(){
        return landscape;
    }


    @Override
    public boolean onHeatMapClicked() {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(CURRENTLY_SELECTED_PLACE,currentlySelectedPlace);
        outState.putParcelable(PLACES, Parcels.wrap(places));

//        if(placeDetailsFragment != null){
//            getSupportFragmentManager().beginTransaction().remove(placeDetailsFragment).commit();
//        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            currentlySelectedPlace = savedInstanceState.getInt(CURRENTLY_SELECTED_PLACE,-1);
            places = Parcels.unwrap(savedInstanceState.getParcelable(PLACES));
        }
    }
}
