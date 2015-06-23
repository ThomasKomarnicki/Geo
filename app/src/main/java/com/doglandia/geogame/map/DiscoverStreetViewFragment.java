package com.doglandia.geogame.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.doglandia.geogame.R;
import com.doglandia.geogame.activity.DiscoverActivity;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

/**
 * Created by Thomas on 6/21/2015.
 */
public class DiscoverStreetViewFragment extends SupportStreetViewPanoramaFragment implements
        OnStreetViewPanoramaReadyCallback, StreetViewPanorama.OnStreetViewPanoramaChangeListener {

    private StreetViewPanorama streetViewPanorama;
    private LatLng currentLocation;

    private Button addLocationButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_street_view_fragment,null);
        addLocationButton = (Button) view.findViewById(R.id.discover_street_view_add_button);
        ((FrameLayout)view.findViewById(R.id.discover_street_view_map_container)).addView(super.onCreateView(inflater, container, savedInstanceState));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getStreetViewPanoramaAsync(this);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DiscoverActivity)getActivity()).onLocationAdded(currentLocation);
            }
        });
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;
        setLocation(currentLocation);
        streetViewPanorama.setOnStreetViewPanoramaChangeListener(this);
    }

    public void setLocation(LatLng latLng){
        if(latLng != null){
            this.currentLocation = latLng;
        }

        if(currentLocation == null || streetViewPanorama == null){
            return;
        }

        streetViewPanorama.setPosition(latLng,1000);
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
        if(streetViewPanoramaLocation == null){
            Log.d(getClass().getSimpleName(),"no location found");
            ((DiscoverActivity)getActivity()).onLocationChangeResult(streetViewPanoramaLocation);
            return;
        }
        currentLocation = streetViewPanoramaLocation.position;
    }
}
