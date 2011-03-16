package edu.csun.ss12.flashcards;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnInitListener{
	
	private EditText mUserName;
	private EditText mPassword;
	private Button btnRegister;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
    private GestureOverlayView gestures;
	private GestureLibrary mLibrary;
	int counter=0;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        mUserName = (EditText)this.findViewById(R.id.Register_EditTextUserName);
        mPassword = (EditText)this.findViewById(R.id.Register_EditTextPassword);
        btnRegister = (Button)this.findViewById(R.id.Register_ButtonCreate);
        btnRegister.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {  
        		String password_MD5 ="";
        		try {
					password_MD5 = functions.MD5(mPassword.getText().toString());
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		MySQL_Connection mysql = new MySQL_Connection();        		
        		Boolean result = mysql.register(mUserName.getText().toString(), password_MD5);
        		if(result){
        			Log.e("AWd","True");
        			Toast toast = Toast.makeText(getBaseContext(), "Register Successfully.", 5);
					toast.show();
					String speech1 = "Register Successfully";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   			    	finish();
        		}
        		else{
        			Log.e("AWd","False");
        			Toast toast = Toast.makeText(getBaseContext(), "Wrong user name or password.", 10);
					toast.show();
        		}
        	}
        }); 
      //Text-to-Speech
        Intent checkIntent = new Intent();
     	  checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     	  startActivityForResult(checkIntent, 0);
     	  tts = new TextToSpeech(this, this);
     	  
     	//Select Username
     	 mUserName.setOnFocusChangeListener(new OnFocusChangeListener(){

   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
   				// TODO Auto-generated method stub
   				if(gainFocus){
   					String speech1 = "Username";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   				}
   			}
          	
          });
     	//Select Password
     	mPassword.setOnFocusChangeListener(new OnFocusChangeListener(){

   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
   				// TODO Auto-generated method stub
   				if(gainFocus){
   					String speech1 = "Password";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   				}
   			}
          	
          });
     	//Select Register
     	btnRegister.setOnFocusChangeListener(new OnFocusChangeListener(){

   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
   				// TODO Auto-generated method stub
   				if(gainFocus){
   					String speech1 = "Create Account";
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
        gestures = (GestureOverlayView) findViewById(R.id.Register_gestures);
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
