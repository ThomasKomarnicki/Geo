package com.doglandia.geogame.server.typeAdapter;

import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Thomas on 7/19/2015.
 */
public class LocateResultTypeAdapter extends TypeAdapter<PlaceLocateResult> {
    @Override
    public void write(JsonWriter out, PlaceLocateResult placeLocateResult) throws IOException {
        Number value;
        if(placeLocateResult.getUser() == -1){
            value = UserAuth.getAuthUserId();
        }else{
            value = placeLocateResult.getUser();
        }
        out.name("user").value(value);
        out.name("location").value(placeLocateResult.getActualLocation().getId());
        out.name("lat").value(placeLocateResult.getGuessedLocation().latitude);
        out.name("lon").value(placeLocateResult.getGuessedLocation().longitude);

    }

    @Override
    public PlaceLocateResult read(JsonReader in) throws IOException {
        PlaceLocateResult placeLocateResult = new PlaceLocateResult();
        double lat = 0;
        double lon = 0;

        in.beginObject();

        while(in.hasNext()){
            switch (in.nextName()){
                case "user":
                    placeLocateResult.setUser(in.nextInt());
                    break;
                case "location":
                    placeLocateResult.setActualLocation(new PlaceTypeAdapter().read(in));
                    break;
                case "lat":
                    lat = in.nextDouble();
                    break;
                case "lon":
                    lon = in.nextDouble();
                    break;

            }
        }

        in.endObject();

        placeLocateResult.setGuessedLocation(new LatLng(lat,lon));

        return placeLocateResult;
    }
}
