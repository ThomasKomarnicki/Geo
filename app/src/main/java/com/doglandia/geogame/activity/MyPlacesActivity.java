package com.doglandia.geogame.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.MyPlacesAdapter;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.MyPlacesFragment;
import com.doglandia.geogame.fragment.PlaceDetailsFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_places_activity);

        new NavigationAdapter(this);
        NavigationAdapter.setUpNavDrawerActivity(this,"My Places");

        landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if(landscape){
            placeDetailsFragment = (PlaceDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.place_details_fragment);
        }

        myPlacesFragment = (MyPlacesFragment) getSupportFragmentManager().findFragmentById(R.id.my_places_fragment);
        myPlacesFragment.getPlaces(0);

        if(places == null) {
            Server.getInstance().getUserLocations(0, new Callback<ArrayList<Place>>() {
                @Override
                public void success(ArrayList<Place> places, Response response) {
                    MyPlacesActivity.this.places = places;
                    Log.d(getLocalClassName(), "got " + places.size() + " places");
                    if (places != null && places.size() == 0) {

                    } else if (places != null) {
                        myPlacesFragment.onPlaceClick(places.get(0),0);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
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
