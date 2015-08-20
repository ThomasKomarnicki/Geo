package com.doglandia.geogame.transmissions.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.doglandia.geogame.R;
import com.doglandia.geogame.transmissions.TransmissionTextListener;

/**
 * is given an array of Strings to play in succession
 * is notified of touch events to the encapsulating fragment/view
 */
public class TransmissionConsole extends FrameLayout {

    private TransmissionText transmissionText;
    private ContinueText continueText;

    private String[] text;
    private int currentTextIndex;

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
        transmissionText.setMessageToDisplay(text);
        transmissionText.startTextAnimation(new TransmissionTextListener() {
            @Override
            public void onTransmissionEnd() {
                continueText.startBlinking();
            }
        });
    }

    public void onScreenClick(){
        continueText.stopBlinking();
        currentTextIndex++;
        startAnimatingText(text[currentTextIndex]);
    }

}
