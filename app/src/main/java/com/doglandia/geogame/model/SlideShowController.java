package com.doglandia.geogame.model;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 9/27/2015.
 */
public class SlideShowController {

    private List<Image> downloadedImages;

    private boolean running;

    public SlideShowController(){
        running = true;
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

        if(smallestCountImage != null) {
            smallestCountImage.shownCount++;
            return smallestCountImage.url;
        }else{
            return null;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public long getChangeDelay(){
        return 7000;
    }

    public void finish() {
        running = false;
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
