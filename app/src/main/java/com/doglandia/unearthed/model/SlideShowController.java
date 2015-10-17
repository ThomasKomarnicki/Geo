package com.doglandia.unearthed.model;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 9/27/2015.
 */
public class SlideShowController {

    private static final String IMAGES = "images";

    private List<Image> downloadedImages;

    private boolean running;
    private boolean awaitingImage = false;

    public SlideShowController(){
        running = true;
        downloadedImages = new ArrayList<>();
    }

    public void onNewImageDownloaded(String url){
        downloadedImages.add(new Image(url));
    }

    public String getNextImageUrl(){
        Image smallestCountImage = null;
        for(int i = 0; i < downloadedImages.size(); i++){
//        for(Image image : downloadedImages){
            Image image = downloadedImages.get(i);
            if(smallestCountImage == null || image.shownCount < smallestCountImage.shownCount){
                smallestCountImage = image;
            }
        }

        if(smallestCountImage != null) {
            smallestCountImage.shownCount++;
            awaitingImage = true;
            return smallestCountImage.url;
        }else{
            return null;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public long getChangeDelay(){
        return 5000;
    }

    public void finish() {
        running = false;
    }

    public void imageSuccessfullyDownloaded() {
        awaitingImage = false;
    }

    public boolean isAwaitingImage() {
        return awaitingImage;
    }

    @Parcel
    public static class Image{
        String url;
        int shownCount;

        public Image(){}

        public Image(String url){
            this.url = url;
            shownCount = 0;
        }
    }

    public boolean shouldDownloadImages(Activity activity){
        ConnectivityManager connManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    public void saveState(Bundle outState){
        outState.putParcelable(IMAGES, Parcels.wrap(downloadedImages));
    }

    public void restoreState(Bundle inState){
        if(inState != null && inState.containsKey(IMAGES)) {
            downloadedImages = Parcels.unwrap(inState.getParcelable(IMAGES));
        }
    }
}
