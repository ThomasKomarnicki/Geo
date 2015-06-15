package com.doglandia.geogame.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.MyPlacesAdapter;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.MyPlacesFragment;
import com.doglandia.geogame.fragment.PlaceDetailsFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.server.Server;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/13/2015.
 */
public class MyPlacesActivity extends AppCompatActivity {


    boolean landscape = false;

    private PlaceDetailsFragment placeDetailsFragment;

    private MyPlacesFragment myPlacesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_places_activity);

        new NavigationAdapter(this);
        NavigationAdapter.setUpNavDrawerActivity(this,"My Places");

        landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        myPlacesFragment = (MyPlacesFragment) getSupportFragmentManager().findFragmentById(R.id.my_places_fragment);
        myPlacesFragment.getPlaces(0);
    }

    public void onPlaceClick(Place place){
        if(landscape){
            placeDetailsFragment.showPlaceDetails(place);
        }else{
            Intent intent = new Intent(this,PlaceDetailsActivity.class);
            startActivity(intent);
        }
    }


}
