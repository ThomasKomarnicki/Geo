package com.doglandia.unearthed.view;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Thomas on 10/2/2015.
 */
public class FadingImageView extends FrameLayout {

    private ImageView imageView1;
    private ImageView imageView2;

//    private ImageView frontImageView;
//    private ImageView backImageView;

    private boolean frontImageViewAnimating = false;
    private boolean backImageViewAnimating = false;

    public FadingImageView(Context context) {
        super(context);
        init();
    }

    public FadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        imageView1 = new ImageView(getContext());
        imageView2 = new ImageView(getContext());

        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        addView(imageView1);
        addView(imageView2);

        imageView1.setAlpha(1f);
        imageView2.setAlpha(0f);
    }

    public ImageView getHiddenImageView(){
        if(imageView1.getAlpha() == 0){
            return imageView1;
        }else if (imageView2.getAlpha() == 0){
            return imageView2;
        }
        return null;
    }

    public ImageView getVisibleImageView(){
        if(imageView1.getAlpha() == 1){
            return imageView1;
        }else if (imageView2.getAlpha() == 1){
            return imageView2;
        }
        return null;
    }

    public boolean isAnimating(){
        return backImageViewAnimating && frontImageViewAnimating;
    }

    public void fadeInNewImage(){
        imageView1.animate()
                .alpha(0f)
                .setDuration(750)
                .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                backImageViewAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                backImageViewAnimating = false;
                onImageViewAnimationEnded();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
        imageView2.animate()
                .alpha(1)
                .setDuration(750)
                .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                frontImageViewAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                frontImageViewAnimating = false;
                onImageViewAnimationEnded();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void onImageViewAnimationEnded(){
        if( backImageViewAnimating == false && frontImageViewAnimating == false){
            // swap front and back
            ImageView oldFront = imageView1;
            ImageView oldBack = imageView2;
            imageView1 = oldBack;
            imageView2 = oldFront;
        }
    }
}
