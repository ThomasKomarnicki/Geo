package com.doglandia.geogame.server.typeAdapter;

import com.doglandia.geogame.model.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Thomas on 7/19/2015.
 */
public class PlaceTypeAdapter extends TypeAdapter<Place> {


    @Override
    public void write(JsonWriter out, Place place) throws IOException {
        out.name("user").value(place.getUserId());
        out.name("lat").value(place.getLatLng().latitude);
        out.name("lon").value(place.getLatLng().longitude);
    }

    @Override
    public Place read(JsonReader in) throws IOException {
        Place place = new Place();

        in.beginObject();

        double lat = 0;
        double lon = 0;

        while(in.hasNext()){
            String name = in.nextName();
            switch (name){
                case "id":
                    place.setId(in.nextInt());
                    break;
                case "user":
                    place.setUserId(in.nextInt());
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

        place.setLatLng(new LatLng(lat,lon));

        return place;
    }


}
