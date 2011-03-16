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
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class Search extends Activity implements OnInitListener {
	private Button  btnSearch;
	private EditText txtKeyword;
	private String keyword;
	private TextView tvNotFound;
	final String PREFERENCES = "FlashcardPreferences";
	private ArrayList<Flashcard> mFlashcardArray;
	private ArrayList<Flashcard> mResult;
	private String mFlashcardFront;
	private String mFlashcardBack;
	private boolean first_time;
	private ListView lvResult;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;

    private GestureOverlayView gestures;
	private GestureLibrary mLibrary;
	int counter=0;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);   
        
        //Text-to-Speech
        Intent checkIntent = new Intent();
    	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
    	startActivityForResult(checkIntent, 0);
    	tts = new TextToSpeech(this, this);
    	
        lvResult=(ListView)findViewById(R.id.Search_ListView);
        first_time = true;   
        tvNotFound = (TextView) this.findViewById(R.id.Search_NotFound);
        txtKeyword = (EditText) this.findViewById(R.id.Search_InputText);
        btnSearch = (Button) this.findViewById(R.id.Search_ButtonSearch);
        
        tvNotFound.setVisibility(android.view.View.GONE);
        btnSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				tvNotFound.setVisibility(android.view.View.GONE);
				keyword = txtKeyword.getText().toString();
				mResult = new ArrayList<Flashcard>();  
				///////////////////////////
				//Download all flashcards (only for the first search) 
				//////////////////////////////////////////
				if(first_time){
					MySQL_Connection mysql = new MySQL_Connection();        
			        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
			        JSONArray jArray = mysql.getFlashCard( preferences.getInt("userId", 6));
					mFlashcardArray = new ArrayList<Flashcard>();
					
			        for(int i=0;i<jArray.length();i++){
			        	JSONObject json_data = null;
				        try {
				        	json_data = jArray.getJSONObject(i);
				        } catch (JSONException e) {
				        	e.printStackTrace();
				        }
				
				        try {						
							mFlashcardFront =json_data.getString("front");
							mFlashcardBack = json_data.getString("back");				
							String flashcardId = json_data.getString("flashcard_id");						
							mFlashcardArray.add(new Flashcard(mFlashcardFront, mFlashcardBack, flashcardId));
				        }
				        catch (JSONException e) {
				        	e.printStackTrace();
				        }				   
			        }
			        first_time = false;
				}
				///End Download
				
				// Handle downloading math equation image event.
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
	        //End Handler
	        
		        for(int i=0;i<mFlashcardArray.size();i++){
					String temp_html = "not found ";
					String temp_front = mFlashcardArray.get(i).getmFront();
					String temp_back =  mFlashcardArray.get(i).getmBack();
					String fc_id =  mFlashcardArray.get(i).getmFlashcardId();
		        	//Remove html part		        	
		        	int index_front_html_beginning = temp_front.indexOf("<img ");
		        	int index_front_html_end = temp_front.indexOf("absmiddle>");
		        	int index_back_html_beginning = temp_back.indexOf("<img ");
		        	int index_back_html_end = temp_back.indexOf("absmiddle>");
		        	
		        	if((index_front_html_beginning!=-1)&&(index_front_html_end!=-1)){
		        		temp_html = temp_front.substring(index_front_html_beginning, index_front_html_end+10);	        		
		        		temp_front = temp_front.replace(temp_html, " ");
		        		System.out.println("Front : " + temp_front);
		        	}		        			        	
		        	if((index_back_html_beginning!=-1)&&(index_back_html_end!=-1)){
		        		temp_html = temp_back.substring(index_back_html_beginning, index_back_html_end+10);
		        		temp_back = temp_back.replace(temp_html, " ");
		        		System.out.println("Back : " + temp_back);
		        	}
		        	// End Remove html part
		        	if((temp_front.indexOf(keyword)!=-1)
		        			||(temp_back.indexOf(keyword)!=-1)){
		        		mResult.add(new Flashcard(temp_front,temp_back,fc_id,i));
		        	}
		        }  
		        ArrayAdapter<Spanned> adapter = new ArrayAdapter<Spanned>(Search.this, R.drawable.list_item);
		        lvResult.setAdapter(adapter);
		        lvResult.setOnItemClickListener(new OnItemClickListener() {
		            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		              
		              System.out.println("Clicked");
		              SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
				      final SharedPreferences.Editor editor = preferences.edit();				      
				      int original_id = mResult.get(position).getmOriginal_Index();
				      String item_front = mFlashcardArray.get(original_id).getmFront();
				      String item_back = mFlashcardArray.get(original_id).getmBack();
				      String item_id = mFlashcardArray.get(original_id).getmFlashcardId();
				      System.out.println("item_front :" + item_front);
				      System.out.println("item_back :" + item_back);
				      System.out.println("item_id :" + item_id);
				      // String group = mFlashcardArray.get(v.getId()).getmGroup();				        
				      editor.putString("flashcardFront", item_front);
				      editor.putString("flashcardBack", item_back);
				      editor.putString("flashcardId", item_id);
				      //editor.putString("flashcardGroup", group);
				      editor.commit();
				      startActivity(new Intent(getBaseContext(), DynamicFlashcard.class));
		            }
		        });
		        if(mResult.size()!=0){
			        for(int i = 0; i<mResult.size();i++){		        	
				      Spanned temp_spanned = Html.fromHtml(mFlashcardArray.get(mResult.get(i).getmOriginal_Index()).getmFront(), imgGetter, null);
				      adapter.add(temp_spanned);		            
			        }
			        adapter.notifyDataSetChanged();
			        String speech1 = mResult.size() +" flashcards found";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
			    }
		        else{
		        	tvNotFound.setVisibility(android.view.View.VISIBLE);
		        	tvNotFound.setText("No results found");
		        	String speech1 = "No results found, please try again";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
		        }
			}        	
        });
        
        //Select the Edit text
        txtKeyword.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean gainFocus) {
				if(gainFocus){
					String speech1 = "please enter your search term";
			    	tts.setLanguage(Locale.US);
			    	tts.speak(speech1, TextToSpeech.QUEUE_FLUSH, null);
				}
			}        	
        });
        
      //Select the Search Button
        btnSearch.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean gainFocus) {
				if(gainFocus){
					String speech1 = "Search";
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
        gestures = (GestureOverlayView) findViewById(R.id.Search_gestures);
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
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                tts = new TextToSpeech(this, this);
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                    TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }
    
    
}