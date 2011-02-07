package edu.csun.ss12.flashcards;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Modify extends Activity implements OnInitListener {

	Spinner spGroups;
	EditText textFront;
	EditText textBack;
	EditText textGroup;
	TextView textViewGroup;
	Button btnCreate;
	Button btnReset;
	private int mUserId;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	final String PREFERENCES = "FlashcardPreferences";
    SharedPreferences preferences;
    
    private String mFront;
    private String mBack;
    private String mId;
    
    
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);
        
        textViewGroup = (TextView)this.findViewById(R.id.Modify_TextViewGroup);
        spGroups = (Spinner)this.findViewById(R.id.Modify_SpinnerGroup);
        textGroup = (EditText)this.findViewById(R.id.Modify_EditTextGroup);
        
        String group = spGroups.getSelectedItem().toString();
        System.out.println(group);
        if (group.equals("Create Group"))
        	textGroup.setVisibility(0); //visible
        else
            textGroup.setVisibility(4); //invisible
    
        // hide widgets for Hung....
        spGroups.setVisibility(8); //gone
        textGroup.setVisibility(8); //gone
        textViewGroup.setVisibility(8);
        	
        textFront = (EditText)this.findViewById(R.id.Modify_EditTextFront);
        textBack = (EditText)this.findViewById(R.id.Modify_EditTextBack);
        btnCreate = (Button)this.findViewById(R.id.Modify_ButtonCreate);
        btnReset = (Button)this.findViewById(R.id.Modify_ButtonReset);
        
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        
    	final String PREFERENCES = "FlashcardPreferences";
        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
       
        // flash card info
        mFront = preferences.getString("flashcardFront", "Card Front");
		mBack = preferences.getString("flashcardBack", "Card Front");
		mId = preferences.getString("flashcardId", "Card ID");
        
		// populate text fields
		textFront.setText(mFront);
		textBack.setText(mBack);
		
		
        mUserId = preferences.getInt("userId", 6);
        
        btnCreate.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// TODO create
        		MySQL_Connection mysql = new MySQL_Connection();        		
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
        		// TODO reset
        		textGroup.setText("");
        		textFront.setText("");
        		textBack.setText(""); 
        	}
        });
      //Text-to-Speech
        Intent checkIntent = new Intent();
     	  checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     	  startActivityForResult(checkIntent, 0);
     	  tts = new TextToSpeech(this, this);
     	  
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
