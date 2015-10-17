package com.doglandia.unearthed.map;

import android.os.Bundle;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thomas on 6/2/2015.
 */
public class StreetViewMapFragment extends SupportStreetViewPanoramaFragment implements OnStreetViewPanoramaReadyCallback{

    private StreetViewPanorama streetViewPanorama;
    private LatLng latLng;
    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;
        streetViewPanorama.setStreetNamesEnabled(false);
        if(latLng != null){
            streetViewPanorama.setPosition(latLng);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getStreetViewPanoramaAsync(this);
    }

    public void setPosition(LatLng latLng){
        this.latLng = latLng;
        if(streetViewPanorama != null) {
            streetViewPanorama.setPosition(latLng);
        }
    }
}
