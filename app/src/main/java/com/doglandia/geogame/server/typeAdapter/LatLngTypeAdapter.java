package com.doglandia.geogame.server.typeAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Thomas on 7/20/2015.
 */
public class LatLngTypeAdapter extends TypeAdapter<LatLng> {
    @Override
    public void write(JsonWriter out, LatLng value) throws IOException {
        out.beginObject();
        out.name("lat").value(value.latitude);
        out.name("lon").value(value.longitude);
        out.endObject();
    }

    @Override
    public LatLng read(JsonReader in) throws IOException {
       double lat = 0;
        double lon = 0;

        in.beginObject();
        while(in.hasNext()){
            switch (in.nextName()){
                case "lat":
                    if(in.peek() == JsonToken.STRING){
                        lat = Double.valueOf(in.nextString());
                    }else{
                        lat = in.nextDouble();
                    }
                    break;
                case "lon":
                    if(in.peek() == JsonToken.STRING){
                        lon = Double.valueOf(in.nextString());
                    }else{
                        lon = in.nextDouble();
                    }
                    break;
            }
        }

        return new LatLng(lat,lon);
    }
}