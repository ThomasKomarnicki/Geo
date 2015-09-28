package com.doglandia.geogame.model;

import com.doglandia.geogame.server.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 9/27/2015.
 */
public class SlideShowInfo {

    private String urlPrefix;
    private List<String> portraitImages;
    private List<String> landscapeImages;

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public List<String> getPortraitImages() {
        return portraitImages;
    }

    public List<String> getLandscapeImages() {
        return landscapeImages;
    }

    public List<String> getFullyQualifiedImageUrls(boolean landscape){
        List<String> fullUrls = new ArrayList<>();
        List<String> partialUrls;
        if(landscape){
            partialUrls = landscapeImages;
        }else{
            partialUrls = portraitImages;
        }

        for(String image : partialUrls){
            fullUrls.add(Server.SERVER_ADDRESS + urlPrefix + image);
        }

        return fullUrls;
    }
}
