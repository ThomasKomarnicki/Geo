package com.doglandia.unearthed.Animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.doglandia.unearthed.R;

public class AnimatorFrameLayout extends FrameLayout {

    public interface AnimationListener{
        void onShrinkStart();
        void onShrinkEnd();
        void onRestoreStart();
        void onRestoreEnd();
    }

    private static final String TAG = "AnimatorFrameLayout";

    private final Rect mClipRect = new Rect();

//    private Paint clipBoundsPaint;

    private ImageView overlayImage;
    private Fragment contentFragment;

    private AnimationListener animationListener;

//    private ClipBoundsAnimator clipBoundsAnimator;
    public AnimatorFrameLayout(Context context) {
        super(context);
        init();
    }

    public AnimatorFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatorFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOverlayImage(Bitmap bitmap){
        overlayImage.setImageBitmap(bitmap);
        overlayImage.setVisibility(View.VISIBLE);
    }

    public void setContentFragment(Fragment fragment){
        this.contentFragment = fragment;
    }

    public int getContentFrameId(){
        return R.id.animator_frame_content_holder;
    }


    private void init(){
        setWillNotDraw(false);
        addView(LayoutInflater.from(getContext()).inflate(R.layout.animator_frame_layout,null));
        overlayImage = (ImageView) findViewById(R.id.animator_frame_image);
//        setElevation(5f);

//        clipBoundsPaint = new Paint();
//        clipBoundsPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        clipBoundsPaint.setStrokeWidth(20);
//        clipBoundsPaint.setColor(Color.BLACK);
//        clipBoundsPaint.setARGB(255, 0, 0, 0);
    }

    public void setOverlayDrawable(Drawable drawable){
        overlayImage.setVisibility(View.VISIBLE);
        overlayImage.setImageDrawable(drawable);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        if (clip()) {
            Log.d(TAG, "clipping canvas");
            canvas.clipRect(mClipRect);

        }else{
            Log.d(TAG,"didn't clip canvas");
        }
        super.onDraw(canvas);
//        canvas.drawRect(mClipRect,clipBoundsPaint);
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

    public void setRespondToClick(){
        overlayImage.setClickable(true);
        overlayImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startRestoreAnimation();
                overlayImage.setClickable(false);
            }
        });
    }

    public void startShrinkAnimation(){
        overlayImage.setVisibility(View.VISIBLE);
        long duration = 400;
        AnimatorSet shrinkAnimation = new AnimatorSet();

//        final float[] values = new float[] {0f, 0.6f};
        ObjectAnimator clipBoundsAnimation = ObjectAnimator.ofFloat(this, "imageCrop", 0f,.6f);

        ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(this,"scale",1f,.35f);

        ObjectAnimator positionAnimation = ObjectAnimator.ofFloat(this,"delta", 0f,.3f);

        shrinkAnimation.playTogether(clipBoundsAnimation,scaleAnimation,positionAnimation);
        shrinkAnimation.setDuration(duration);
        shrinkAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                overlayImage.setVisibility(View.VISIBLE);
//                contentFragment.getFragmentManager().beginTransaction().hide(contentFragment).commit();
                if(animationListener != null){
                    animationListener.onShrinkStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setRespondToClick();
                if(animationListener != null){
                    animationListener.onShrinkEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        shrinkAnimation.start();
    }

    public void startRestoreAnimation(){
        long duration = 400;
        AnimatorSet restoreAnimation = new AnimatorSet();

        final float[] values = new float[] {.6f, 0f};
        ObjectAnimator clipBoundsAnimation = ObjectAnimator.ofFloat(this, "imageCrop", 0.6f, 0f);

        ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(this,"scale",.35f,1f);

        ObjectAnimator positionAnimation = ObjectAnimator.ofFloat(this,"delta", .3f,0f);

        restoreAnimation.playTogether(clipBoundsAnimation,scaleAnimation,positionAnimation);
        restoreAnimation.setDuration(duration);
        restoreAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                overlayImage.setVisibility(View.VISIBLE);
                if(animationListener != null){
                    animationListener.onRestoreStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                contentFragment.getFragmentManager().beginTransaction().show(contentFragment).commit();
                post(new Runnable() {
                    @Override
                    public void run() {
                        overlayImage.setVisibility(View.INVISIBLE);
                    }
                });
                if(animationListener != null){
                    animationListener.onRestoreEnd();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        restoreAnimation.start();
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
    }

    public void setDelta(float value){
        Log.d(TAG,"setting position X: "+(getWidth()*value)+", Y: "+(getHeight()*value));
        setX(getWidth()*value);
        setY(getHeight()*value);
    }


    public void setScale(float value){
        Log.d(TAG,"setting Scale");
        setScaleX(value);
        setScaleY(value);
    }

    public void setImageCrop(float value) {
        Log.d(TAG,"setting Image Crop");
        // nothing to do if there's no drawable set

        // nothing to do if no dimensions are known yet
        final int width = getWidth();
        final int height = getHeight();
        if (width <= 0 || height <= 0) return;

        final int sideLength = (width < height) ? width : height;

        int clipAmount;
        if(width < height){
            clipAmount = (int) (value *(height - sideLength));
            mClipRect.set(getLeft(), getTop() + clipAmount, getRight(), getBottom() - clipAmount);
        }else{
            clipAmount = (int) (value *(width - sideLength));
            mClipRect.set(getLeft() + clipAmount, getTop(), getRight() - clipAmount, getBottom());
        }

        Log.d(TAG,"clip rect = "+mClipRect.toShortString());

        invalidate();
    }

}
