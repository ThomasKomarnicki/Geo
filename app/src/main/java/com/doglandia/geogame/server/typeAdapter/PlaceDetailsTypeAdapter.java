package com.doglandia.geogame.server.typeAdapter;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceDetails;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsTypeAdapter extends TypeAdapter<PlaceDetails> {
    @Override
    public void write(JsonWriter out, PlaceDetails value) throws IOException {

    }

    @Override
    public PlaceDetails read(JsonReader in) throws IOException {
        PlaceDetails placeDetails = new PlaceDetails();

        in.beginObject();

        while(in.hasNext()){
            switch (in.nextName()){
                case "average_distance":
                    placeDetails.setAverageDistance((int) in.nextDouble());
                    break;
                case "best_distance":
                    placeDetails.setBestDistance(in.nextInt());
                    break;
                case "place":
//                    in.beginObject();
                    Place place = new PlaceTypeAdapter().read(in);
                    placeDetails.setPlace(place);
//                    in.endObject();
                    break;
                case "location_guesses":
                    List<LatLng> guesses = new ArrayList<>();

                    in.beginArray();
                    while(in.hasNext()){
                        in.beginObject();
                        double lat = 0;
                        double lon = 0;
                        while (in.hasNext()){
                            switch (in.nextName()){
                                case "lat":
                                    lat = in.nextDouble();
                                    break;
                                case "lon":
                                    lon = in.nextDouble();
                                    break;
                                default:
                                    in.skipValue();
                                    break;
                            }
                        }
                        guesses.add(new LatLng(lat,lon));
                        in.endObject();
                    }


                    in.endArray();

                    placeDetails.setLocationGuesses(guesses);
                    break;
            }
        }

        in.endObject();
        return placeDetails;
    }
}
