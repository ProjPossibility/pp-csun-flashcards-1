package edu.csun.ss12.flashcards;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Window;
import android.widget.TextView;

public class About extends Activity implements OnInitListener {
	
	TextView text ;
	private TextToSpeech tts;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about);
        
        text = (TextView) findViewById(R.id.About_text);
        text.setText("Flashcard App for Android \n" +
        		"Created by CSUN team for SS12 \n\n" +
        		"Tips: \n" +
        		"In Browse, tap on item will load back of the card, long tap will make the app read card. \n" +
        		"When viewing the flashcard, the app will read the flashcard with single tap, long tap is for modifying the flashcard.");
        
        //Text-to-Speech
        Intent checkIntent = new Intent();
     	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     	startActivityForResult(checkIntent, 0);
     	tts = new TextToSpeech(this, this);
     	tts.setLanguage(Locale.US);
    	tts.speak(text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        
    }
    @Override
    public void onInit(int arg0) {
    	// TODO Auto-generated method stub
    	
    }    

}
