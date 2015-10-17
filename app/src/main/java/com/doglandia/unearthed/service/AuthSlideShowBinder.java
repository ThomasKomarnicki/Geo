package com.doglandia.unearthed.service;

import android.os.Binder;

/**
 * Created by Thomas on 9/27/2015.
 */
public class AuthSlideShowBinder extends Binder {

    public interface OnImageDownloadedListener{
        void onImageDownloaded(String url);
    }

    private AuthSlideShowService service;

    public AuthSlideShowBinder(AuthSlideShowService authSlideShowService){
        this.service = authSlideShowService;

    }

    public void setOnImageDownloadedListener(OnImageDownloadedListener onImageDownloadedListener){
        service.setOnImageDownloadedListener(onImageDownloadedListener);
    }
}
