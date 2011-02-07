package edu.csun.ss12.flashcards;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DynamicFlashcard extends Activity implements OnInitListener  {

	String mFront;
	String mBack;
	String mId;
	
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
     	
        ScrollView mScrollView = new ScrollView(this);
        LinearLayout mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mScrollView.addView(mLinearLayout);
		TextView front = new TextView(this);
		TextView back = new TextView(this);
		
    	final String PREFERENCES = "FlashcardPreferences";
        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        mFront = preferences.getString("flashcardFront", "Card Front");
		mBack = preferences.getString("flashcardBack", "Card Front");
		mId = preferences.getString("flashcardId", "Card ID");
		
		front.setTextSize(50);
		front.setText(mFront);
		front.setClickable(true);
		// TODO text to speech
		back.setTextSize(50);
		back.setBackgroundColor(Color.GRAY);
		back.setText(mBack);
		back.setClickable(true);
		
		
		front.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String speech1 = mFront;
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
				}
	       });
		
		front.setOnLongClickListener(new OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				// TODO pass flash card to create
				
		        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
		        final SharedPreferences.Editor editor = preferences.edit();
				editor.putString("flashcardFront", mFront);
				editor.putString("flashcardBack", mBack);
				editor.putString("flashcardId", mId);
				editor.commit();
				startActivity(new Intent(getBaseContext(), Modify.class));
				return true;
			}
			
		});
		
		back.setOnLongClickListener(new OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				// TODO pass flash card to create
		        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
		        final SharedPreferences.Editor editor = preferences.edit();
				editor.putString("flashcardFront", mFront);
				editor.putString("flashcardBack", mBack);
				editor.putString("flashcardId", mId);
				editor.commit();
				
				startActivity(new Intent(getBaseContext(), Modify.class));
				return true;
			}
			
		});
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String speech1 = mBack;
		    	tts.setLanguage(Locale.US);
		    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
			}
       });
		
		mLinearLayout.addView(front);
		mLinearLayout.addView(back);
		setContentView(mScrollView);
    }

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}
}