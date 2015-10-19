package com.doglandia.unearthed.transmissions.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.transmissions.TransmissionFragment;
import com.doglandia.unearthed.transmissions.TransmissionTextListener;

/**
 * is given an array of Strings to play in succession
 * is notified of touch events to the encapsulating fragment/view
 */
public class TransmissionConsole extends FrameLayout {

    private static final String TAG = "TransmissionConsole";
    private TransmissionText transmissionText;
    private ContinueText continueText;

    private String[] text;
    private int currentTextIndex;

    private TransmissionTextListener transmissionTextListener;

    private int textIndex = 0;

    public TransmissionConsole(Context context) {
        super(context);
        init();
    }

    public TransmissionConsole(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TransmissionConsole(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.transmission_console,null);
        addView(view);
        transmissionText = (TransmissionText) findViewById(R.id.transmission_text);
        continueText = (ContinueText) findViewById(R.id.continue_text);

    }

    public void animateText(String[] text){
        this.text = text;
        currentTextIndex = 0;
        startAnimatingText(text[currentTextIndex]);

    }

    private void startAnimatingText(String text){
        if(transmissionTextListener != null){
            transmissionTextListener.onNewTextShown(textIndex);
            textIndex++;
        }
        transmissionText.setMessageToDisplay(text);
        transmissionText.startTextAnimation(new TransmissionTextListener() {
            @Override
            public void onTransmissionEnd() {
                Log.d(TAG,"transmission Ended");
                continueText.startBlinking();
            }

            @Override
            public void onNewTextShown(int textIndex) {

            }
        });
        continueText.stopBlinking();
    }

    public void onScreenClick(){
        continueText.stopBlinking();
        if(transmissionText.isAnimating()){
            transmissionText.showFullText();
        }
        else if(text != null && currentTextIndex < text.length-1) {
            currentTextIndex++;
            startAnimatingText(text[currentTextIndex]);
        }else{
            transmissionTextListener.onTransmissionEnd();
        }
    }

    public void setTransmissionTextListener(TransmissionTextListener transmissionTextListener) {
        this.transmissionTextListener = transmissionTextListener;
    }
}
