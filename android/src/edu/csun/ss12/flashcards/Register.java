package edu.csun.ss12.flashcards;

import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity {
	
	private EditText mUserName;
	private EditText mPassword;
	private Button btnRegister;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mUserName = (EditText)this.findViewById(R.id.Register_EditTextUserName);
        mPassword = (EditText)this.findViewById(R.id.Register_EditTextPassword);
        btnRegister = (Button)this.findViewById(R.id.Register_ButtonCreate);
        btnRegister.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {  
        		String password_MD5 ="";
        		try {
					password_MD5 = functions.MD5(mPassword.getText().toString());
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		MySQL_Connection mysql = new MySQL_Connection();        		
        		mysql.register(mUserName.getText().toString(), password_MD5);
        		
        	}
        });  
    }
    
}
