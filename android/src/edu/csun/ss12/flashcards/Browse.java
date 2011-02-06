package edu.csun.ss12.flashcards;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Browse extends Activity {

	TextView mFront;
	TextView mBack;
	ScrollView mScrollView;
	LinearLayout mLinearLayout;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.browse);
         // initialize text
         //mFront = (TextView)this.findViewById(R.id.Browse_TextViewFront);
        
        mScrollView = new ScrollView(this);
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mScrollView.addView(mLinearLayout);
		
		
		boolean rowColor = true;
        MySQL_Connection mysql = new MySQL_Connection();
        JSONArray jArray = mysql.getFlashCard(6);
        for(int i=0;i<jArray.length();i++){
        	
        	JSONObject json_data = null;
	        try {
	        	json_data = jArray.getJSONObject(i);
	        } catch (JSONException e) {
	        	e.printStackTrace();
	        }
	
	        try {
	        //Log.i("log_tag","id: "+json_data.getInt("flashcard_id")+
	        //       ", name: "+json_data.getString("front"));
	        	
				TextView mDynamicFlashcard  = new TextView(this);
				mDynamicFlashcard.setText(json_data.getString("front"));// get front of flash card
				mDynamicFlashcard.setTextSize(50);
				mDynamicFlashcard.setClickable(true);
				
				if(rowColor==true){
					mDynamicFlashcard.setBackgroundColor(Color.BLACK);
					rowColor=false;
				} else {
					mDynamicFlashcard.setBackgroundColor(Color.GRAY);
					rowColor=true;
				}
				
				mLinearLayout.addView(mDynamicFlashcard);
				
				//mLinearLayout.
				
	            //mFront.setText(json_data.getString("front"));
	        
	        } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        }   

	        this.setContentView(mScrollView);

        	}

    	}

	}
