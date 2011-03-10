package edu.csun.ss12.flashcards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
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
	Button btnModify;
	Button btnReset;
	Button btnDelete;
	private int mUserId;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	final String PREFERENCES = "FlashcardPreferences";
    SharedPreferences preferences;
    
    private String mFront;
    private String mBack;
    private String mId;
    private int flashcard_Id;
    ArrayList<Flashcard> mFlashcardArray = new ArrayList<Flashcard>();
    String mFlashcardFront ;
	String mFlashcardBack ;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        btnModify = (Button)this.findViewById(R.id.Modify_ButtonModify);
        btnReset = (Button)this.findViewById(R.id.Modify_ButtonReset);
        btnDelete = (Button)this.findViewById(R.id.Modify_ButtonDelete);
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        
    	final String PREFERENCES = "FlashcardPreferences";
        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
       
        // flash card info
        mFront = preferences.getString("flashcardFront", "Card Front");
		mBack = preferences.getString("flashcardBack", "Card Front");
		mId = preferences.getString("flashcardId", "Card ID");
		flashcard_Id = Integer.parseInt(mId);
        
		//Get data form server for a single card 
		MySQL_Connection mysql = new MySQL_Connection();       
        
        JSONArray jArray = mysql.getFrontandBack( flashcard_Id);       
        
        JSONObject json_data = null;
	    try {
	        json_data = jArray.getJSONObject(0);
	    } catch (JSONException e) {
	       	e.printStackTrace();
	    }	
	    try {				
			mFlashcardFront =json_data.getString("front");
			mFlashcardBack = json_data.getString("back");					
	    } catch (JSONException e) {
	       	e.printStackTrace();
	    }   
	    
	    //Display content of flashcard
	    textFront.setText(mFlashcardFront);
	    textBack.setText(mFlashcardBack);		
        mUserId = preferences.getInt("userId", 6);     
        
        //Modify Button
        btnModify.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		MySQL_Connection mysql = new MySQL_Connection();        		
        		if(mysql.modify(mId, textFront.getText().toString(), textBack.getText().toString())){
        			
        			Log.e("AWd","True");
        			Toast toast = Toast.makeText(getBaseContext(), "Modify Successfully.", 5);
					toast.show();
					String speech1 = "Modify Successfully";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   			    	finish();
        		}else{
        			System.out.println("FALSE");
        		}
        	}
        });
        //Reset button
        btnReset.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		textGroup.setText("");
        		textFront.setText(mFlashcardFront);
        		textBack.setText(mFlashcardBack); 
        	}
        });
        //Delete button
        btnDelete.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//////////////////////////////////
        		
        		//////////////////////////////////
        		confirm_delete();
        	}
        });
        
        /////////////////
        //Text-to-Speech
        /////////////////
        Intent checkIntent = new Intent();
     	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     	startActivityForResult(checkIntent, 0);
     	tts = new TextToSpeech(this, this);
     	  
     	//Select Group
     	 textGroup.setOnFocusChangeListener(new OnFocusChangeListener(){
   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
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
   				if(gainFocus){
   					String speech1 = "Back of Card";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   				}
   			}
          	
          });
     	//Select Modify
     	btnModify.setOnFocusChangeListener(new OnFocusChangeListener(){

   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
   				if(gainFocus){
   					String speech1 = "Modify";
   			    	tts.setLanguage(Locale.US);
   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
   				}
   			}
          	
          });
     	//Select Reset
     	btnReset.setOnFocusChangeListener(new OnFocusChangeListener(){
   			@Override
   			public void onFocusChange(View arg0, boolean gainFocus) {
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
    }
    public void startMenu(){
    	this.startActivity(new Intent(getBaseContext(), Menu.class));
    }
    private void confirm_delete(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to delete this flashcard?")
    	       .setCancelable(false)
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   MySQL_Connection mysql = new MySQL_Connection();   
    	        		System.out.println(textGroup.getText().toString());
    	        		if(mysql.delete(flashcard_Id)){      			
    	        			Log.e("AWd","True");
    	        			Toast toast = Toast.makeText(getBaseContext(), "Delete Successfully.", 5);
    						toast.show();
    						String speech1 = "Delete Successfully";
    	   			    	tts.setLanguage(Locale.US);
    	   			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
    	   			    	//finish();
    	   			    	startMenu();
    	        		}else{
    	        			System.out.println("FALSE");
    	        		} 
    	           }
    	       })
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
    }
}
