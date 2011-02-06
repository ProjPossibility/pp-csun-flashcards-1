package edu.csun.ss12.flashcards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends Activity {
	
	Button btnCreate;
	Button btnBrowse;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
       btnBrowse = (Button)this.findViewById(R.id.Menu_ButtonBrowse);
       btnBrowse.setOnClickListener(new OnClickListener() {
       	@Override
       	public void onClick(View v) {
       		startBrowse();
       	}
       });
       
       btnCreate = (Button)this.findViewById(R.id.Menu_ButtonCreate);
       btnCreate.setOnClickListener(new OnClickListener() {
          	@Override
          	public void onClick(View v) {
          		startCreate();
          	}
          });
        
    }
    
    
    public void startCreate(){
    	this.startActivity(new Intent(getBaseContext(), Create.class));
    }
    
    public void startBrowse(){
    	this.startActivity(new Intent(getBaseContext(), Browse.class));
    }
}
