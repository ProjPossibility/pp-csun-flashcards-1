package edu.csun.ss12.flashcards;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Login extends Activity {
   
	private Button btnExit;
	private Button btnLogin;
	private EditText mUserName;
	private EditText mPassword;
	private CheckBox mSaveInfo;
	final static String PREFERENCES = "FlashcardPreferences";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences preferences = this.getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        
        mUserName = (EditText)this.findViewById(R.id.EditTextUserName);
        mPassword = (EditText)this.findViewById(R.id.EditTextPassword);
        mSaveInfo = (CheckBox)this.findViewById(R.id.CheckBoxSaveAccountInfo);
        btnExit = (Button)this.findViewById(R.id.ButtonExit);
        btnLogin = (Button)this.findViewById(R.id.ButtonLogin);
        
        // Check for saved user information
        if( preferences.getBoolean("saveAccountInfo", false) == true) { 
        	mUserName.setText(preferences.getString("userName", ""));
        	mPassword.setText(preferences.getString("password", ""));
        	mSaveInfo.setChecked(true);
        	editor.putBoolean("saveAccountInfo", true);
        	editor.commit();
        } else {
        	mSaveInfo.setChecked(false);
        	editor.putBoolean("saveAccountInfo", false);
        	editor.commit();
        }
        
        
        // Exit button listener
        btnExit.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        		if(mSaveInfo.isChecked()){
                	editor.putBoolean("saveAccountInfo", true);
        			Editable accountInfo = mUserName.getText();
        			editor.putString("userName", accountInfo.toString());
        			accountInfo = mPassword.getText();
        			editor.putString("password", accountInfo.toString());
        			editor.commit();
        		}
        		finish();
        	}
        });
        
        // Login button listener
        btnLogin.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		if(mSaveInfo.isChecked()){
                	editor.putBoolean("saveAccountInfo", true);
        			Editable accountInfo = mUserName.getText();
        			editor.putString("userName", accountInfo.toString());
        			accountInfo = mPassword.getText();
        			editor.putString("password", accountInfo.toString());
        			editor.commit();
        			
        			boolean loginSuccess = true;
        			if( loginSuccess == true ){
        				startMenu();
        			}
        			
        		}
        	}
        });  
    }
    

    public void startMenu(){
    	this.startActivity(new Intent(getBaseContext(), Menu.class));
    }
}