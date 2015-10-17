package com.doglandia.unearthed.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.model.SlideShowInfo;
import com.doglandia.unearthed.server.Server;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AuthSlideShowService extends Service{

    private static final String TAG = "AuthSlideShowService";
    private AuthSlideShowBinder binder;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private SlideShowImageManager slideShowImageManager;

    private AuthSlideShowBinder.OnImageDownloadedListener onImageDownloadedListener;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new AuthSlideShowBinder(this);
        slideShowImageManager = new SlideShowImageManager();

        HandlerThread thread = new HandlerThread("ServiceStartArguments", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        Log.d(TAG,"srated slide show service");
        Server.getInstance().getSlideShowInfo(new Callback<SlideShowInfo>() {
            @Override
            public void success(SlideShowInfo slideShowInfo, Response response) {
                Log.d(TAG,"got slide show info");
                List<String> urls = slideShowInfo.getFullyQualifiedImageUrls(getResources().getBoolean(R.bool.show_two_pane_layout));
                startDownloadingImages(urls);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"bound to service");
        return binder;
    }

    public void setOnImageDownloadedListener(AuthSlideShowBinder.OnImageDownloadedListener onImageDownloadedListener){
        this.onImageDownloadedListener = onImageDownloadedListener;
    }

    public void startDownloadingImages(List<String> imageUrls){
        slideShowImageManager.addUrls(imageUrls);
        for(String url : imageUrls){
            sendDownloadMessage(url);
        }
    }

    private void sendDownloadMessage(String url){
        Message message = mServiceHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        message.setData(bundle);
        mServiceHandler.sendMessage(message);
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.

            String url = msg.getData().getString("url");
            if (!slideShowImageManager.isUrlDownloaded(url)) {

                Log.d(TAG, "fetching url: " + url);
                Picasso.with(AuthSlideShowService.this).load(url).fetch();
                slideShowImageManager.onUrlDownloaded(url);
                if (onImageDownloadedListener != null) {
                    onImageDownloadedListener.onImageDownloaded(url);
                }
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
        }
    }



}
