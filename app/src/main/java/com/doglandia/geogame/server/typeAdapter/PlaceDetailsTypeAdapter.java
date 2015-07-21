package com.doglandia.geogame.server.typeAdapter;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceDetails;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Thomas on 7/20/2015.
 */
public class PlaceDetailsTypeAdapter extends TypeAdapter<PlaceDetails> {
    @Override
    public void write(JsonWriter out, PlaceDetails value) throws IOException {

    }

    @Override
    public PlaceDetails read(JsonReader in) throws IOException {
        PlaceDetails placeDetails = new PlaceDetails();
        // TODO not needed

        Place place = new Place();
        double lat = 0;
        double lon = 0;

        in.beginObject();

        while(in.hasNext()){
            switch (in.nextName()){
                case "id":
                    place.setId(in.nextInt());
                    break;
                case "user":
                    place.setUserId(in.nextInt());
                    break;
                case "lat":
                    lat = in.nextDouble();

            }
        }

        in.endObject();
        return placeDetails;
    }
}
