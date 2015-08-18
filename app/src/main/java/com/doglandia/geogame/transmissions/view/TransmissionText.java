package com.doglandia.geogame.transmissions.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Thomas on 8/16/2015.
 */
public class TransmissionText extends TextView {

    private String messageToDisplay;
    private int numCharsDisplayed = 0;
    public interface OnTransmissionTextAnimationEnd {
        void onTransmissionEnded();
    }

    private ObjectAnimator objectAnimator;

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
        objectAnimator = ObjectAnimator.ofInt(this,"numCharsDisplayed",0,numCharsDisplayed);
    }

    public void startTextAnimation(){
        objectAnimator.start();
    }

    public void showFullText(){
        stopTextAnimation();
        setText(messageToDisplay);
        invalidate();
    }

    public void stopTextAnimation(){

    }
}
