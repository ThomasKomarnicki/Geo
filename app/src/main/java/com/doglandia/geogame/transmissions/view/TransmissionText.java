package com.doglandia.geogame.transmissions.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import com.doglandia.geogame.transmissions.TransmissionTextListener;

/**
 * Created by Thomas on 8/16/2015.
 */
public class TransmissionText extends TextView {

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
        setText("");

    }

    public void setMessageToDisplay(String message){
        this.messageToDisplay = message;
        numCharsDisplayed = 0;
        objectAnimator = ObjectAnimator.ofInt(this,"numCharsDisplayed",0,numCharsDisplayed);
    }

    public void startTextAnimation(final TransmissionTextListener transmissionTextListener){
        this.transmissionTextListener = transmissionTextListener;
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
    }

    public void showFullText(){
        stopTextAnimation();
        setText(messageToDisplay);
        invalidate();
    }

    public void stopTextAnimation(){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        setText(messageToDisplay.substring(0,numCharsDisplayed));
        super.onDraw(canvas);
    }
}
