package com.doglandia.geogame.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.MyPlacesAdapter;
import com.doglandia.geogame.adapter.NavigationAdapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_places_activity);

        new NavigationAdapter(this);

        landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void onPlaceClick(Place place){
        if(landscape){
            placeDetailsFragment.showPlaceDetails(place);
        }else{
            Intent intent = new Intent(this,PlaceDetailsActivity.class);
            startActivity(intent);
        }
    }


}
