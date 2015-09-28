package com.doglandia.geogame.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 9/27/2015.
 */
public class SlideShowController {

    List<Image> downloadedImages;

    public SlideShowController(){
        downloadedImages = new ArrayList<>();
    }

    public void onNewImageDownloaded(String url){
        downloadedImages.add(new Image(url));
    }

    public String getNextImageUrl(){
        Image smallestCountImage = null;
        for(Image image : downloadedImages){
            if(smallestCountImage == null || image.shownCount < smallestCountImage.shownCount){
                smallestCountImage = image;
            }
        }

        return smallestCountImage.url;
    }

    private class Image{
        String url;
        int shownCount;

        public Image(String url){
            this.url = url;
            shownCount = 0;
        }
    }
}
