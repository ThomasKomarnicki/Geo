package com.doglandia.geogame.map;

import android.os.Bundle;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;

/**
 * Created by Thomas on 6/2/2015.
 */
public class StreetViewMapFragment extends SupportStreetViewPanoramaFragment implements OnStreetViewPanoramaReadyCallback{

    private StreetViewPanorama streetViewPanorama;
    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getStreetViewPanoramaAsync(this);
    }
}
