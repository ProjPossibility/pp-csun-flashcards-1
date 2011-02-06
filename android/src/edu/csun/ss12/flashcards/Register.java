package edu.csun.ss12.flashcards;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity {
	
	EditText txtUserName;
	EditText txtPassword;
	Button btnCreate;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        txtUserName = (EditText)this.findViewById(R.id.Register_EditTextUserName);
        txtPassword = (EditText)this.findViewById(R.id.Register_EditTextPassword);
        btnCreate = (Button)this.findViewById(R.id.Register_ButtonCreate);
        
        
        btnCreate.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// TODO create
        	}
        });
    }
    
}
