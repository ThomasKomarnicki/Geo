package com.doglandia.unearthed.transmissions.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.doglandia.unearthed.R;

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

        if(alphaAnimation == null){
            alphaAnimation = AnimatorInflater.loadAnimator(getContext(),R.animator.blinking_text);
            alphaAnimation.setTarget(this);
            alphaAnimation.start();
        }else{
            alphaAnimation.end();
            alphaAnimation.start();
        }
//        alphaAnimation = ObjectAnimator.ofFloat(this,"alpha",0,1);

//        alphaAnimation.setDuration(500);

    }

    public void stopBlinking(){
        setAlpha(0);
        if(alphaAnimation != null){
//            alphaAnimation.cancel();
            alphaAnimation.end();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d(TAG,"drawing");
    }
}
