package com.doglandia.geogame.transmissions.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.doglandia.geogame.transmissions.TransmissionTextListener;

/**
 * Created by Thomas on 8/16/2015.
 */
public class TransmissionText extends TextView {

    private static final String TAG = "TransmissionText";
    private String messageToDisplay;
    private int numCharsDisplayed = 0;

    private ObjectAnimator objectAnimator;
    private TransmissionTextListener transmissionTextListener;

    public TransmissionText(Context context) {
        super(context);
        init();
    }

    public TransmissionText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TransmissionText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setWillNotDraw(false);
        setText("");

    }

    public void setMessageToDisplay(String message){
        this.messageToDisplay = message;
        numCharsDisplayed = 0;
        objectAnimator = ObjectAnimator.ofInt(this,"numCharsDisplayed",0,messageToDisplay.length());
        objectAnimator.setDuration(messageToDisplay.length() * 32);
    }

    public void setNumCharsDisplayed(int value){
        Log.d(TAG,"numCharsDisplayed set to "+value);
        this.numCharsDisplayed = value;
        invalidate();
    }

    public void startTextAnimation(final TransmissionTextListener transmissionTextListener){
        this.transmissionTextListener = transmissionTextListener;
        objectAnimator.setStartDelay(250);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                transmissionTextListener.onTransmissionEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
        setWillNotDraw(false);
    }

    public void showFullText(){
        stopTextAnimation();
        setText(messageToDisplay);
    }

    public void stopTextAnimation(){
        objectAnimator.end();
        transmissionTextListener.onTransmissionEnd();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "drawing transmission text, " + numCharsDisplayed + " / " + messageToDisplay.length());
        if(!messageToDisplay.equals(getText().toString())) {
            setText(messageToDisplay.substring(0, numCharsDisplayed));
        }
        super.onDraw(canvas);
    }

    public boolean isAnimating() {
        return objectAnimator != null && objectAnimator.isRunning();
    }
}
