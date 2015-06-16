package com.doglandia.geogame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.map.PlaceHeatMapFragment;
import com.doglandia.geogame.model.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Thomas on 6/13/2015.
 */
public class PlaceDetailsFragment extends Fragment implements OnMapReadyCallback{

    private TextView cityTv;
    private TextView countryTv;

    private SupportMapFragment headerLiteMapFragment;

    private GoogleMap headerGoogleMap;

    private Place place;

    private PlaceHeatMapFragment heatMapFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_details_fragment,null);

        cityTv = (TextView) view.findViewById(R.id.place_details_city);
        countryTv = (TextView) view.findViewById(R.id.place_details_country);

        headerLiteMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.place_details_lite_map);
        headerLiteMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                headerGoogleMap = googleMap;
                initHeaderMap();
            }
        });

        heatMapFragment = (PlaceHeatMapFragment) getFragmentManager().findFragmentById(R.id.place_details_heat_map);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showPlaceDetails(Place place){
        // TODO
        initHeaderMap();

        if(place.getState() != null){
            cityTv.setText(place.getCity()+", "+place.getState());
        }else {
            cityTv.setText(place.getCity());
        }

        countryTv.setText(place.getCountry());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        initHeaderMap();
    }

    private void initHeaderMap(){
        if(place == null || headerGoogleMap == null){
            return;
        }

        headerGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
    }
}
