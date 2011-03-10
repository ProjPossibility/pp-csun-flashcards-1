package edu.csun.ss12.flashcards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
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
	final String PREFERENCES = "FlashcardPreferences";
	private ArrayList<Flashcard> mFlashcardArray;
	private ArrayList<Flashcard> mResult;
	private String mFlashcardFront;
	private String mFlashcardBack;
	private boolean first_time;
	private ListView lvResult;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);   
        
        lvResult=(ListView)findViewById(R.id.Search_ListView);
        first_time = true;            
        txtKeyword = (EditText) this.findViewById(R.id.Search_InputText);
        btnSearch = (Button) this.findViewById(R.id.Search_ButtonSearch);
        btnSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				keyword = txtKeyword.getText().toString();
				mResult = new ArrayList<Flashcard>();  
				String temp_html = "not found ";
				String temp = new String();
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
		        	Flashcard temp_flashcard = mFlashcardArray.get(i);
		        	//Remove html part		        	
		        	/*int index_front_html_beginning = temp_flashcard.getmFront().indexOf("<img ");
		        	int index_front_html_end = temp_flashcard.getmFront().indexOf("absmiddle>");
		        	int index_back_html_beginning = temp_flashcard.getmBack().indexOf("<img ");
		        	int index_back_html_end = temp_flashcard.getmBack().indexOf("absmiddle>");
		        	
		        	if((index_front_html_beginning!=-1)&&(index_front_html_end!=-1)){
		        		temp_html = temp_flashcard.getmFront().substring(index_front_html_beginning, index_front_html_end+10);	        		
		        		temp = temp_flashcard.getmFront();
		        		temp = temp.replace(temp_html, " ");
		        		System.out.println("Front : " + temp);
		        		temp_flashcard.setmFront(temp);
		        	}
		        	
		        	
		        	if((index_back_html_beginning!=-1)&&(index_back_html_end!=-1)){
		        		temp_html = temp_flashcard.getmBack().substring(index_back_html_beginning, index_back_html_end+10);
		        		temp = temp_flashcard.getmBack();
		        		temp = temp.replace(temp_html, " ");
		        		System.out.println("Back : " + temp);
		        		temp_flashcard.setmBack(temp);
		        	}*/
		        	// End Remove html part
		        	if((temp_flashcard.getmFront().indexOf(keyword)!=-1)
		        			||(temp_flashcard.getmBack().indexOf(keyword)!=-1)){
		        		temp_flashcard.setmOriginal_Index(i);
		        		mResult.add(temp_flashcard);
		        	}
		        }  
		        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1);
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
		        for(int i = 0; i<mResult.size();i++){
		        	////////////////////////////////////		        	
			      String temp_spanned = Html.fromHtml(mFlashcardArray.get(mResult.get(i).getmOriginal_Index()).getmFront(), imgGetter, null).toString();
			     		        	/////////////////////////////////////
		        	adapter.add(temp_spanned);		            
		        }
		       /* for(int i = 0; i<Original_Data.size();i++){
		        	////////////////////////////////////		        	
			      
			     
		        	/////////////////////////////////////
		        	adapter.add(Original_Data.get(i).getmFront());		            
		        }*/		        
		        adapter.notifyDataSetChanged();
			}        	
        });
	}
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}
    
    
}