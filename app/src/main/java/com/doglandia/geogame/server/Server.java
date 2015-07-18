package com.doglandia.geogame.server;

import retrofit.RestAdapter;


/**
 * Created by Thomas on 6/7/2015.
 */
public class Server {

    public static ServerInterface getInstance(){
        if(serverInterface == null){
//            serverInterface = new LocalMockServer();

            serverInterface = new RestAdapter.Builder()
                    .setEndpoint("http://192.168.0.4:8000")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
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
