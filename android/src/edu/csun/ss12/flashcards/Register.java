package edu.csun.ss12.flashcards;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
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
    }
    @Override
    public void onInit(int arg0) {
    	// TODO Auto-generated method stub
    	
    }
}
