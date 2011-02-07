package edu.csun.ss12.flashcards;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;

public class Menu extends Activity implements OnInitListener{
	
	Button btnCreate;
	Button btnBrowse;
	Button btnExit;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
       btnBrowse = (Button)this.findViewById(R.id.Menu_ButtonBrowse);
       btnBrowse.setOnClickListener(new OnClickListener() {
       	@Override
       	public void onClick(View v) {
       		startBrowse();
       	}
       });
       
       btnCreate = (Button)this.findViewById(R.id.Menu_ButtonCreate);
       btnCreate.setOnClickListener(new OnClickListener() {
          	@Override
          	public void onClick(View v) {
          		startCreate();
          	}
          });
       
       btnExit = (Button)this.findViewById(R.id.Menu_ButtonExit);
       btnExit.setOnClickListener(new OnClickListener() {
          	@Override
          	public void onClick(View v) {
          		finish();
          	}
          });
     //Text-to-Speech
      Intent checkIntent = new Intent();
   	  checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
   	  startActivityForResult(checkIntent, 0);
   	  tts = new TextToSpeech(this, this);
     //Select Browse
       btnBrowse.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View arg0, boolean gainFocus) {
				// TODO Auto-generated method stub
				if(gainFocus){
					String speech1 = "Browse";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
       	
       });
     //Select Create
       btnCreate.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean gainFocus) {
				// TODO Auto-generated method stub
				if(gainFocus){
					String speech1 = "Create Flashcard";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
       });
     //Select Sign Out
       btnExit.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View arg0, boolean gainFocus) {
				// TODO Auto-generated method stub
				if(gainFocus){
					String speech1 = "Sign Out";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
       	
       });
    }
    
    
    public void startCreate(){
    	this.startActivity(new Intent(getBaseContext(), Create.class));
    }
    
    public void startBrowse(){
    	this.startActivity(new Intent(getBaseContext(), Browse.class));
    }
    @Override
    public void onInit(int arg0) {
    	// TODO Auto-generated method stub
    	
    }
}
