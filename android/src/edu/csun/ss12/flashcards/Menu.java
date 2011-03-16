package edu.csun.ss12.flashcards;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Menu extends Activity implements OnInitListener{
	
	private Button btnCreate;
	private Button btnAbout;
	private Button btnBrowse;
	private Button btnExit;
	private Button btnSearch;
	private GestureOverlayView gestures;
	private GestureLibrary mLibrary;
	int leftright=0;
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
       		startAbout();
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
       
       
       //Gesture   
       mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
       if (!mLibrary.load()) {
           finish();
       }
       gestures = (GestureOverlayView) findViewById(R.id.Menu_gestures);
       gestures.addOnGesturePerformedListener(new OnGesturePerformedListener(){
			@Override
			public void onGesturePerformed(GestureOverlayView overlay,Gesture gesture) {
				ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
			    if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
			        String action = predictions.get(0).name;
			        if ("left_right".equals(action)) {
			        	for(int i = 0; i<((leftright % 5)+1);i++){
				            functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_RIGHT);
			        	}
			        	leftright++;
			        } else if ("right_left".equals(action)) {
			        	if(leftright>0){
			        		leftright--;
			        	}
			        	else{
			        		leftright=4;
			        	}
			        	for(int i = 0; i<((leftright % 5)+1);i++){
				            functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_RIGHT);
			        	}
			        } 
			        else if ("click".equals(action)) {
			        	if(leftright % 5==0){
			        		for(int i = 0; i<5;i++){
					            functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_RIGHT);
				        	}
			        	}
			        	else{
				        	for(int i = 0; i<((leftright % 5));i++){
					            functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_RIGHT);
				        	}
			        	}
			        	functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_CENTER);
			        }
			    }
			}        	
       });
     //End Gesture
       
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
    
    public void startAbout(){
    	this.startActivity(new Intent(getBaseContext(), About.class));
    }
    @Override
    public void onInit(int arg0) {
    }
    
}
