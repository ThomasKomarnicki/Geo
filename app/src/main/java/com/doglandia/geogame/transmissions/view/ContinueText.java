package com.doglandia.geogame.transmissions.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 8/16/2015.
 */
public class ContinueText extends TextView {

    private Animator alphaAnimation;

    public ContinueText(Context context) {
        super(context);
        init();
    }

    public ContinueText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContinueText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setAlpha(0);
    }

    public void startBlinking(){
        alphaAnimation = AnimatorInflater.loadAnimator(getContext(),R.animator.blinking_text);
        alphaAnimation.start();


    }

    public void stopBlinking(){
        setAlpha(0);
        alphaAnimation.cancel();
    }
}
