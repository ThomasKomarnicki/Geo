package com.doglandia.geogame.Animation;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * Created by Thomas on 7/24/2015.
 */
public class ClipBoundsAnimator {

    private static final String TAG = "ClipBoundsAnimator";

    private int left = 0;
    private int right;
    private int top = 0;
    private int bottom;

    private float sideLength;

    private long duration = 500;
    private long lastDraw;
    
    boolean animating = false;
    
    private View view;

    public ClipBoundsAnimator(View view){
        this.view = view;

        right = view.getWidth();
        bottom = view.getHeight();

        sideLength = view.getWidth();
    }

    public void startAnimation(){
        animating = true;
        lastDraw = 0;
    }

    public void draw(Canvas canvas){
        if(animating) {

            long delta = System.nanoTime()/1000 - lastDraw;
            if(lastDraw == 0){
                delta = 0;
            }
            float verticalChange = (delta/duration) * (view.getHeight() - sideLength);
            top = (int) (top + verticalChange);
            bottom = (int) (bottom - verticalChange);
            Log.d(TAG, "vertical change = "+verticalChange);
            Log.d(TAG, "clipping canvas to "+left+", "+top+", "+right+", "+bottom);
            canvas.clipRect(left,top,right,bottom);

            lastDraw = System.nanoTime()/1000;

            if(delta >= duration){
                animating = false;
                onDoneAnimating();
            }

            view.invalidate();
        }
        
    }
    
    private void onDoneAnimating(){
//        view.setClipBounds(new Rect(left,top,right,bottom));
    }
}
