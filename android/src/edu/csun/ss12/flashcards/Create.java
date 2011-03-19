package edu.csun.ss12.flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Create extends Activity implements OnInitListener {

	
	Spinner spGroups;
	EditText textFront;
	EditText textBack;
	EditText textGroup;
	Button btnCreate;
	Button btnReset;
	Button btnSpeak;
	private int mUserId;
	private TextToSpeech tts;
	final String PREFERENCES = "FlashcardPreferences";
    SharedPreferences preferences;
    private GestureOverlayView gestures;
	private GestureLibrary mLibrary;
	int counter=0;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create);
        
        /////////////////////////
        //Text-to-Speech
        /////////////////////////
        Intent checkIntent = new Intent();
     	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     	startActivityForResult(checkIntent, 0);
     	tts = new TextToSpeech(this, this);     	
     	/////////////////////////
        //End Text-to-Speech
        /////////////////////////
        spGroups = (Spinner)this.findViewById(R.id.Create_SpinnerGroup);
        textGroup = (EditText)this.findViewById(R.id.Create_EditTextGroup);
        
        String group = spGroups.getSelectedItem().toString();
        System.out.println(group);
        if (group.equals("Create Group"))
        	textGroup.setVisibility(0); //visible
        else {
        	textGroup.setVisibility(8); // gone
            // textGroup.setVisibility(4); //invisible
        }

        	
        textFront = (EditText)this.findViewById(R.id.Create_EditTextFront);
        textBack = (EditText)this.findViewById(R.id.Create_EditTextBack);
        btnCreate = (Button)this.findViewById(R.id.Create_ButtonCreate);
        btnReset = (Button)this.findViewById(R.id.Create_ButtonReset);
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        mUserId = preferences.getInt("userId", 6);
        
        
       ////////////////////
        //On click Creat Button: Get data from Group, Front, Back, and send it to server. If success, show the toast and say " Create Successfully
        ///////////////////////
        btnCreate.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        		MySQL_Connection mysql = new MySQL_Connection();   
        		System.out.println(textGroup.getText().toString());
        		if(mysql.create(mUserId, textGroup.getText().toString(), textFront.getText().toString(), textBack.getText().toString())){      			
        			Log.e("AWd","True");
        			Toast toast = Toast.makeText(getBaseContext(), "Create Successfully.", 5);
					toast.show();
					String speech1 = "Create Successfully";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   			    	finish();
        		}else{
        			System.out.println("FALSE");
        		}
        	}
        });
        //////////////////////
        /// End On Click Create Button
        //////////////////////
        
        //////////////////////
        /// On Click Reset Button : reset all field to empty
        //////////////////////
        btnReset.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		textGroup.setText("");
        		textFront.setText("");
        		textBack.setText(""); 
        	}
        });
        //////////////////////
        /// End On Click Reset Button
        //////////////////////
     	  
     	//Select Group
     	 textGroup.setOnFocusChangeListener(new OnFocusChangeListener(){

   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
   				// TODO Auto-generated method stub
   				if(gainFocus){
   					String speech1 = "Group";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   				}
   			}
          	
          });
     	//Select Front of Card
     	textFront.setOnFocusChangeListener(new OnFocusChangeListener(){

   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
   				// TODO Auto-generated method stub
   				if(gainFocus){
   					String speech1 = "Front of Card";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   				}
   			}
          	
          });
     	//Select Back of Card
     	textBack.setOnFocusChangeListener(new OnFocusChangeListener(){

   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
   				// TODO Auto-generated method stub
   				if(gainFocus){
   					String speech1 = "Back of Card";
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
   					String speech1 = "Create";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   				}
   			}
          	
          });
     	//Select Create
     	btnReset.setOnFocusChangeListener(new OnFocusChangeListener(){

   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
   				// TODO Auto-generated method stub
   				if(gainFocus){
   					String speech1 = "Reset";
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
        gestures = (GestureOverlayView) findViewById(R.id.Create_gestures);
        gestures.addOnGesturePerformedListener(new OnGesturePerformedListener(){
 			@Override
 			public void onGesturePerformed(GestureOverlayView overlay,Gesture gesture) {
 				ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
 			    if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
 			        String action = predictions.get(0).name;
 			        if ("left_right".equals(action)) {
 			        	if(counter%6==0||counter%6==1){
 			        		functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_DOWN);
 			        	}
 			        	else
 			        	{
	 			        	for(int i = 0; i<((counter % 6)+2);i++){
	 				            functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_DOWN);
	 			        	}
 			        	}
 			        	System.out.println(counter);
 			        	counter++; 			        	
 			        } else if ("right_left".equals(action)) {
 			        	if(counter>0){
 			        		counter--;
 			        	}
 			        	else{
 			        		counter=5;
 			        	}
 			        	for(int i = 0; i<((counter %6));i++){
 				            functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_DOWN);
 			        	}
 			        } 
 			        else if ("click".equals(action)) {
 			        	if(counter % 6==0){
 			        		for(int i = 0; i<6;i++){
 					            functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_DOWN);
 				        	}
 			        	}
 			        	else{
 				        	for(int i = 0; i<((counter % 6));i++){
 					            functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_DOWN);
 				        	}
 			        	}
 			        	functions.InjectKeys(android.view.KeyEvent.KEYCODE_DPAD_CENTER);
 			        }
 			    }
 			}        	
        });
      //End Gesture
        
    }
    @Override
    public void onInit(int arg0) {
    	// TODO Auto-generated method stub
    	
    }    

}
