package com.doglandia.geogame.server;

import android.content.Context;
import android.os.AsyncTask;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.util.Util;

import java.util.List;

/**
 * Created by Thomas on 8/2/2015.
 */
public class GeoCodeTask extends AsyncTask<List<Place>,Void,Void> {

    private Context context;
    public GeoCodeTask(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(List<Place>... params) {
        Util.GeoCodePlaces(params[0],context);
        return null;
    }

}
