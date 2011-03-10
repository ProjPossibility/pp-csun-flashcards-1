package edu.csun.ss12.flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	Spinner spGroups;
	EditText textFront;
	EditText textBack;
	EditText textGroup;
	Button btnCreate;
	Button btnReset;
	Button btnSpeak;
	private int mUserId;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	final String PREFERENCES = "FlashcardPreferences";
    SharedPreferences preferences;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create);
        
        //Text-to-Speech
        Intent checkIntent = new Intent();
     	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     	startActivityForResult(checkIntent, 0);
     	tts = new TextToSpeech(this, this);
     	
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
        
        
       /* spGroups.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String speech1 = spGroups.getSelectedItem().toString();
			    tts.setLanguage(Locale.US);
			    tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
			    
		        String group = spGroups.getSelectedItem().toString();
		        if (group.equals("Create Group"))
		        	textGroup.setVisibility(0); //visible
		        else {
		        	textGroup.s group;
		        	textGroup.setVisibility(8); // gone
		            // textGroup.setVisibility(4); //invisible
		        }
		      //  setContentView(mScrollView);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				String speech1 = spGroups.getSelectedItem().toString();
			    tts.setLanguage(Locale.US);
			    tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
			    
		        String group = spGroups.getSelectedItem().toString();
		        if (group.equals("Create Group"))
		        	textGroup.setVisibility(0); //visible
		        else {
		        	textGroup.setVisibility(8); // gone
		            // textGroup.setVisibility(4); //invisible
		        }
			}
        });
        
        */
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
        
        btnReset.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		textGroup.setText("");
        		textFront.setText("");
        		textBack.setText(""); 
        	}
        });

     	  
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
    }
    @Override
    public void onInit(int arg0) {
    	// TODO Auto-generated method stub
    	
    }    

}
