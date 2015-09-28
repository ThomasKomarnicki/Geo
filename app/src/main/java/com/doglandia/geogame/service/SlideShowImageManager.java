package com.doglandia.geogame.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 9/27/2015.
 */
public class SlideShowImageManager {

    private List<String> imageUrls;
    private List<String> downloadedUrls;

    public SlideShowImageManager(){
        imageUrls = new ArrayList<>();
        downloadedUrls = new ArrayList<>();
    }

    public void onUrlDownloaded(String url){
        downloadedUrls.add(url);
    }


    public void addUrls(List<String> imageUrls) {
        this.imageUrls.addAll(imageUrls);
    }
}
