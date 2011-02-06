package edu.csun.ss12.flashcards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Menu extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }
    
    
    public void startCreate(){
    	this.startActivity(new Intent(getBaseContext(), Create.class));
    }
    
    public void startBrowse(){
    	this.startActivity(new Intent(getBaseContext(), Browse.class));
    }
}
