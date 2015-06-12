package com.doglandia.geogame.server;

/**
 * Created by Thomas on 6/7/2015.
 */
public class Server {

    public static ServerInterface getInstance(){
        if(serverInterface == null){
            serverInterface = new LocalMockServer();
        }
        return serverInterface;
    }

    private static ServerInterface serverInterface;

    public Server(ServerInterface serverInterface){
        this.serverInterface = serverInterface;
    }



}
