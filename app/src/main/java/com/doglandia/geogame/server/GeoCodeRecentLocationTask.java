package com.doglandia.geogame.server;

import android.content.Context;
import android.os.AsyncTask;

import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.util.Util;

import java.util.List;

public class GeoCodeRecentLocationTask extends AsyncTask<List<PlaceLocateResult>,Void,Void> {

    private Context context;
    public GeoCodeRecentLocationTask(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(List<PlaceLocateResult>... params) {
        Util.GeoCodeLocationGuess(params[0],context);
        return null;
    }
}
