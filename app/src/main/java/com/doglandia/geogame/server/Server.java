package com.doglandia.geogame.server;

import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.server.typeAdapter.PlaceTypeAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


/**
 * Created by Thomas on 6/7/2015.
 */
public class Server {

    private static final String HOME_DEV_SERVER = "http://192.168.0.4:8000";
    public static ServerInterface getInstance(){
        if(serverInterface == null){
//            serverInterface = new LocalMockServer();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Place.class,new PlaceTypeAdapter())
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            serverInterface = new RestAdapter.Builder()
                    .setEndpoint("http://192.168.0.4:8000")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(new GsonConverter(gson))
                    .build()
                    .create(ServerInterface.class);
        }
        return serverInterface;
    }

    private static ServerInterface serverInterface;

    public Server(ServerInterface serverInterface){
        this.serverInterface = serverInterface;
    }



}
