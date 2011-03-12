package edu.csun.ss12.flashcards;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends Activity implements OnInitListener{
	
	Button btnCreate;
	Button btnAbout;
	Button btnBrowse;
	Button btnExit;
	Button btnSearch;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dashboard);
        
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
       btnSearch = (Button)this.findViewById(R.id.Menu_ButtonSearch);
       btnSearch.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			startSearch();			
		}    	   
       });
       btnAbout = (Button)this.findViewById(R.id.Menu_ButtonAbout);
       btnAbout.setOnClickListener(new OnClickListener() {
       	@Override
       	public void onClick(View v) {
       		//ToDo :  
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
				if(gainFocus){
					String speech1 = "Create Flashcard";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
       });
     //Select Search
       btnSearch.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean gainFocus) {
				if(gainFocus){
					String speech1 = "Search Flashcards";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
       });
       //Select About
       btnAbout.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean gainFocus) {
				if(gainFocus){
					String speech1 = "About us";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
       });
     //Select Sign Out
       btnExit.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean gainFocus) {
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
    
    public void startSearch(){
    	this.startActivity(new Intent(getBaseContext(), Search.class));
    }
    @Override
    public void onInit(int arg0) {
    }
}
