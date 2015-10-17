package com.doglandia.unearthed.server.typeAdapter;

import android.util.Log;

import com.doglandia.unearthed.model.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Thomas on 7/19/2015.
 */
public class PlaceTypeAdapter extends TypeAdapter<Place> {


    @Override
    public void write(JsonWriter out, Place place) throws IOException {
        out.beginObject();

        out.name("id").value(place.getId());
        out.name("date_added").value(place.getDateAdded());
        out.name("lat").value(place.getLatLng().latitude);
        out.name("lon").value(place.getLatLng().longitude);
        out.endObject();
    }

    @Override
    public Place read(JsonReader in) throws IOException {
        Place place = new Place();

//        Log.d("PlaceTypeAdater", "peek before begin object: " + in.peek().name());
        if(in.peek().ordinal() == JsonToken.NULL.ordinal()){
            in.nextNull();
            return null;
        }

        in.beginObject();

        double lat = 0;
        double lon = 0;

        while(in.hasNext()){

//            if(in.peek() == JsonToken.NAME) {
            Log.d("PlaceTypeAdapter",in.peek().name());
                String name = in.nextName();
                switch (name) {
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
                    case "date_added":
                        place.setDateAdded(in.nextString());
                        break;
//                    case "place":
//                        place.setId(in.nextInt());
//                        break;
                }
//            }
        }

        in.endObject();

        place.setLatLng(new LatLng(lat,lon));

        return place;
    }


}
