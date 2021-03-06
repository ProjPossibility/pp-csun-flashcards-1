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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/***
 * Browse Activity: Download all flashcards of the user and display the front of the card
 * @author hung
 *
 */
public class Browse extends Activity implements OnInitListener {

	boolean rowColor = true;
	TextView mFront;
	TextView mBack;
	ScrollView mScrollView;
	LinearLayout mLinearLayout;
	LinearLayout mParentLinearLayout;
	LinearLayout mTitleBarLinearLayout;
	ImageView mTitleBar;
	View mPaddingView;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
        ////////////////
        //Text-to-Speech
        ///////////////
        Intent checkIntent = new Intent();
     	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     	startActivityForResult(checkIntent, 0);
     	tts = new TextToSpeech(this, this);
        ///////////////////
     	//End Text-To-Speech
     	///////////////////
     	
     	///////////////
     	//Layout
     	///////////////
        mScrollView = new ScrollView(this);
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mScrollView.addView(mLinearLayout);
		mParentLinearLayout = new LinearLayout(this);
		mTitleBarLinearLayout = new LinearLayout(this);
		mTitleBar = new ImageView(this);
		mPaddingView = new View(this);		
		/////////////
		///End Layout
		////////////
		

		//////////////////////
		//Get data form server
		//////////////////////
		MySQL_Connection mysql = new MySQL_Connection();        
        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        JSONArray jArray = mysql.getFlashCard( preferences.getInt("userId", 6));
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
				mFlashcardFront =json_data.getString("front");
				mFlashcardBack = json_data.getString("back");				
				String flashcardId = json_data.getString("flashcard_id");				
				mFlashcardArray.add(new Flashcard(mFlashcardFront, mFlashcardBack, flashcardId));
				mDynamicFlashcard.setTextSize(50);
				mDynamicFlashcard.setClickable(true);
				mDynamicFlashcard.setTextColor(Color.WHITE);
				mDynamicFlashcard.setGravity(Gravity.TOP);
				// Download math equation image.
				ImageGetter imgGetter = new ImageGetter() {
	                public Drawable getDrawable(String source) {
	                        HttpGet get = new HttpGet(source);
	                        DefaultHttpClient client = new DefaultHttpClient();
	                        Drawable drawable = null;
	                        try {
	                                 HttpResponse response = client.execute(get);
	                                InputStream stream = response.getEntity().getContent();
	                                FileOutputStream fileout = new FileOutputStream(new File(getFilesDir()+"/test.gif"));
	                                int read = stream.read();
	                                while (read != -1) {
	                                        fileout.write(read);
	                                        read = stream.read();
	                                }
	                                fileout.flush();
	                                fileout.close();
	                                drawable = Drawable.createFromPath(getFilesDir()+"/test.gif");
	                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
	                        } catch (ClientProtocolException e) {
	                                e.printStackTrace();
	                        } catch (IOException e) {
	                                e.printStackTrace();
	                        }
	                        return drawable;
	                }
	        };
		      mDynamicFlashcard.setText(Html.fromHtml(mFlashcardFront, imgGetter, null));
		      mDynamicFlashcard.setPadding(7, 7, 7, 7);
		    //////////////////////
		    //End Get data form server
		    //////////////////////
		      
		    //////////////////////
			//Color of the row
			//////////////////////
			
		      if(rowColor==true){
					mDynamicFlashcard.setBackgroundColor(Color.BLACK);
					rowColor=false;
				} else {
					mDynamicFlashcard.setBackgroundColor(Color.GRAY);
					rowColor=true;
				}
		      //////////////////////
		      //End Color of the row
		      //////////////////////	
		      
		      //////////////////////
		      //Long Click: When long click on a row, the content of the row will be read
		      //////////////////////
				mDynamicFlashcard.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
				        String speech = mFlashcardArray.get(v.getId()).getmFront();
				        speech = functions.getSpeech(speech);
				        tts.setLanguage(Locale.US);
				    	tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
						return true;
					}});
				//////////////////////
			    //End Long Click
			    //////////////////////
				
				//////////////////////
			    //Click: When click on a row, Front and Back of the flashcard will be shown
			    //////////////////////
				mDynamicFlashcard.setOnClickListener(new OnClickListener() {
					@Override
		        	public void onClick(View v) {
				        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
				        final SharedPreferences.Editor editor = preferences.edit();
				        String front = mFlashcardArray.get(v.getId()).getmFront();
				        String back = mFlashcardArray.get(v.getId()).getmBack();
				        String id = mFlashcardArray.get(v.getId()).getmFlashcardId();
				       // String group = mFlashcardArray.get(v.getId()).getmGroup();
				        
						editor.putString("flashcardFront", front);
						editor.putString("flashcardBack", back);
						editor.putString("flashcardId", id);
						//editor.putString("flashcardGroup", group);
						editor.commit();

				    	startActivity(new Intent(getBaseContext(), DynamicFlashcard.class));
		        	}
		        });
				//////////////////////
			    //End Click
			    //////////////////////
				
				//Draw the layout to screen
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
