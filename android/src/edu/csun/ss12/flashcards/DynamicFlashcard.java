package edu.csun.ss12.flashcards;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DynamicFlashcard extends Activity {

	String mFront;
	String mBack;
	
//	DynamicFlashcard(String front, String back){
//		mFront = front;
//		mBack = back;
//	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.browse);
         // initialize text
         //mFront = (TextView)this.findViewById(R.id.Browse_TextViewFront);
        
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
		
		front.setTextSize(50);
		front.setText(mFront);
		front.setClickable(true);
		// TODO text to speech
		back.setTextSize(50);
		back.setBackgroundColor(Color.GRAY);
		back.setText(mBack);
		back.setClickable(true);
		
		mLinearLayout.addView(front);
		mLinearLayout.addView(back);
		setContentView(mScrollView);
		
		
    }
}