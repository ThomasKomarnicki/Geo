package com.doglandia.unearthed.transmissions.view;

import android.animation.Animator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.transmissions.TransmissionTextListener;

import java.util.ArrayList;

/**
 * Created by Thomas on 8/16/2015.
 */
public class TransmissionText extends FrameLayout {

    private static final String TAG = "TransmissionText";
    private String messageToDisplay;
    private int numCharsDisplayed = 0;

    private ObjectAnimator objectAnimator;
    private TransmissionTextListener transmissionTextListener;

    private TextView transmissionText;
    private TextView ghostText;

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.transmission_text, null);
        transmissionText = (TextView) view.findViewById(R.id.transmission_text_main);
        ghostText = (TextView) view.findViewById(R.id.transmission_text_ghost);

        addView(view);

        setWillNotDraw(false);
        transmissionText.setText("");



    }

    /**
     *
     * @param tv
     */
    private void measureText(TextView tv){
        TextPaint textPaint = tv.getPaint();

        final String[] words = messageToDisplay.split(" ");
        ArrayList<String> resolvedLines = new ArrayList<>();

        StringBuilder currentLine = new StringBuilder();

        for(int i = 0; i < words.length; i++){
            String word = words[i] + " ";
            currentLine.append(word);
            int viewWidth = getWidth();
            if(textPaint.measureText(currentLine.toString()) > viewWidth){
                currentLine.delete(currentLine.length()-word.length()-1,currentLine.length()); // -1 is for the last space
                resolvedLines.add(currentLine.toString());
                currentLine = new StringBuilder();
                i--; // will cause stack overflow if one word is longer than width
            }
        }

        StringBuilder newMessage = new StringBuilder();
        for(String string : resolvedLines){
            newMessage.append(string);
            newMessage.append("\n");
        }

        if(currentLine.length() > 0){
            newMessage.append(currentLine.toString());
        }

        messageToDisplay = newMessage.toString();

    }

    public void setMessageToDisplay(String message){
        this.messageToDisplay = message;
        numCharsDisplayed = 0;
        objectAnimator = ObjectAnimator.ofInt(this,"numCharsDisplayed",0,messageToDisplay.length());
        objectAnimator.setDuration(messageToDisplay.length() * 32); // add a letter every 2 ish frames

        postDelayed(new Runnable() {
            @Override
            public void run() {
                measureText(transmissionText);

            }
        }, 32);
    }

    public void setNumCharsDisplayed(int value){
//        Log.d(TAG,"numCharsDisplayed set to "+value);
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
        transmissionText.setText(messageToDisplay);
    }

    public void stopTextAnimation(){
        objectAnimator.end();
        transmissionTextListener.onTransmissionEnd();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.d(TAG, "drawing transmission text, " + numCharsDisplayed + " / " + messageToDisplay.length());
        if(messageToDisplay != null && !messageToDisplay.equals(transmissionText.getText().toString())) {
            transmissionText.setText(messageToDisplay.substring(0, numCharsDisplayed));
        }
        super.onDraw(canvas);
    }

    public boolean isAnimating() {
        return objectAnimator != null && objectAnimator.isRunning();
    }
}
