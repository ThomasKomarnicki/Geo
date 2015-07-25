package com.doglandia.geogame.Animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by Thomas on 7/24/2015.
 */
public class AnimatorFrameLayout extends FrameLayout {

    private static final String TAG = "AnimatorFrameLayout";

    private final Rect mClipRect = new Rect();

//    private ClipBoundsAnimator clipBoundsAnimator;
    public AnimatorFrameLayout(Context context) {
        super(context);
        initClipping();
    }

    public AnimatorFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClipping();
    }

    public AnimatorFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initClipping();
    }


    private void initClipping(){
        setWillNotDraw(false);
        post(new Runnable() {
            @Override public void run() {
                setImageCrop(0.5f);

            }
        });
    }

    public void startClipAnimation(){
        toggle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (clip()) {
//            Log.d(TAG,"clipping canvas");
            canvas.clipRect(mClipRect);
        }else{
//            Log.d(TAG,"didnt clip canvas");
        }
        super.onDraw(canvas);
    }

    private boolean clip() {
        // true if clip bounds have been set aren't equal to the view's bounds
        return !mClipRect.isEmpty() && !clipEqualsBounds();
    }

    private boolean clipEqualsBounds() {
        final int width = getWidth();
        final int height = getHeight();
        // whether the clip bounds are identical to this view's bounds (which effectively means no clip)
        return mClipRect.width() == width && mClipRect.height() == height;
    }

    private void toggle() {
        // toggle between [0...0.5] and [0.5...0]
        Log.d(TAG,"starting object animator");
        final float[] values = clipEqualsBounds() ? new float[] { 0f, 0.5f } : new float[] { 0.5f, 0f };
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "imageCrop", values);
        objectAnimator.setDuration(500);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG,"animation play time = "+animation.getCurrentPlayTime());
                Log.d(TAG,"clip rect = "+mClipRect.toShortString());
            }
        });
        objectAnimator.start();

    }

    public void setImageCrop(float value) {
//        Log.d(TAG,"setting Image Crop");
        // nothing to do if there's no drawable set

        // nothing to do if no dimensions are known yet
        final int width = getWidth();
        final int height = getHeight();
        if (width <= 0 || height <= 0) return;

        // construct the clip bounds based on the supplied 'value' (which is assumed to be within the range [0...1])
        final int clipWidth = (int) (value * width);
        final int clipHeight = (int) (value * height);
        final int left = clipWidth / 2;
        final int top = clipHeight / 2;
        final int right = width - left;
        final int bottom = height - top;

        // set clipping bounds
        mClipRect.set(left, top, right, bottom);
        // schedule a draw pass for the new clipping bounds to take effect visually
//        Log.d(TAG,"invalidating");
        invalidate();
    }
}
