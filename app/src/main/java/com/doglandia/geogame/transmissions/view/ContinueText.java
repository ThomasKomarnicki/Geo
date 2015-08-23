package com.doglandia.geogame.transmissions.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 8/16/2015.
 */
public class ContinueText extends TextView {

    private static final String TAG = "ContinueText";
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
        setWillNotDraw(false);
        setAlpha(0);
        setText("Tap To Continue");
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
//        Log.d(TAG, "set alpha");
        invalidate();
    }

    public void startBlinking(){
//        setAlpha(1);
        setVisibility(View.VISIBLE);
        alphaAnimation = AnimatorInflater.loadAnimator(getContext(),R.animator.blinking_text);
        alphaAnimation.setTarget(this);
//        alphaAnimation = ObjectAnimator.ofFloat(this,"alpha",0,1);

//        alphaAnimation.setDuration(500);
        alphaAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG,"started blink animation");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG,"ended blink animation");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        alphaAnimation.start();
    }

    public void stopBlinking(){
        setAlpha(0);
        alphaAnimation.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d(TAG,"drawing");
    }
}
