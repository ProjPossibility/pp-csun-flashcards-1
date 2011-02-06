package edu.csun.ss12.flashcards;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Browse extends Activity implements OnInitListener {

	TextView mFront;
	TextView mBack;
	ScrollView mScrollView;
	LinearLayout mLinearLayout;
	String mFlashcardFront;
	String mFlashcardBack;
	ArrayList<Flashcard> mFlashcardArray;
	int index=0;
	final String PREFERENCES = "FlashcardPreferences";
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.browse);
         // initialize text
         //mFront = (TextView)this.findViewById(R.id.Browse_TextViewFront);
        
        
        //Text-to-Speech
        Intent checkIntent = new Intent();
     	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     	startActivityForResult(checkIntent, 0);
     	tts = new TextToSpeech(this, this);
        
        mScrollView = new ScrollView(this);
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mScrollView.addView(mLinearLayout);
		
		
		boolean rowColor = true;
        MySQL_Connection mysql = new MySQL_Connection();
        JSONArray jArray = mysql.getFlashCard(6);
        
		mFlashcardArray = new ArrayList<Flashcard>();
		
        for(int i=0;i<jArray.length();i++){
        	this.index = i;
        	JSONObject json_data = null;
	        try {
	        	json_data = jArray.getJSONObject(i);
	        } catch (JSONException e) {
	        	e.printStackTrace();
	        }
	
	        try {

				TextView mDynamicFlashcard  = new TextView(this);
				mDynamicFlashcard.setId(i);
//				mFlashcardFront =json_data.getString("front");
//				mFlashcardBack = json_data.getString("back");
				String front = json_data.getString("front");
				String back = json_data.getString("back");
				
				mFlashcardArray.add(new Flashcard(front, back));
				
				mDynamicFlashcard.setText(front);// get front of flash card
				mDynamicFlashcard.setTextSize(50);
				mDynamicFlashcard.setClickable(true);
				
				if(rowColor==true){
					mDynamicFlashcard.setBackgroundColor(Color.BLACK);
					rowColor=false;
				} else {
					mDynamicFlashcard.setBackgroundColor(Color.GRAY);
					rowColor=true;
				}
				
				mDynamicFlashcard.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						// TODO text to speech
				        String speech = mFlashcardArray.get(v.getId()).getmFront();
				    	tts.setLanguage(Locale.US);
				    	tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
						return true;
					}});
				
				mDynamicFlashcard.setOnClickListener(new OnClickListener() {
					@Override
		        	public void onClick(View v) {
		        		// TODO display card
				        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
				        final SharedPreferences.Editor editor = preferences.edit();
				        String front = mFlashcardArray.get(v.getId()).getmFront();
				        String back = mFlashcardArray.get(v.getId()).getmBack();
				        
						editor.putString("flashcardFront", front);
						editor.putString("flashcardBack", back);
						editor.commit();

				    	startActivity(new Intent(getBaseContext(), DynamicFlashcard.class));
		        	}
		        });
				
				mLinearLayout.addView(mDynamicFlashcard);
	        
	        } catch (JSONException e) {
	        	e.printStackTrace();
	        }   

	        this.setContentView(mScrollView);

        	}

    	}


	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}
    
    


	}
