package edu.csun.ss12.flashcards;

import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Login extends Activity {
   
	private Button btnExit;
	private Button btnLogin;
	private Button btnRegister;
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
        
        // initalize layout
        mUserName = (EditText)this.findViewById(R.id.Login_EditTextUserName);
        mPassword = (EditText)this.findViewById(R.id.Login_EditTextPassword);
        mSaveInfo = (CheckBox)this.findViewById(R.id.Login_CheckBoxSaveAccountInfo);
        btnExit = (Button)this.findViewById(R.id.Login_ButtonExit);
        btnLogin = (Button)this.findViewById(R.id.Login_ButtonLogin);
        btnRegister = (Button)this.findViewById(R.id.Login_ButtonRegister);
        
        
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
        			
        			/////////////////////////////
        			MySQL_Connection mysql = new MySQL_Connection();
        			String[] login_info = mysql.getLogin("hung");
        			//Log.d("hung", login_info[0]);  
        			//login_info = new String[]{"1","hung","123"};
        			boolean loginSuccess = false;
        			try {
						String password_MD5 = functions.MD5(mPassword.getText().toString());
						if(password_MD5.compareTo(login_info[2])==0) loginSuccess = true;
						Log.d("A",password_MD5);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.d("A",login_info[2]);
					Log.d("A",Boolean.toString(loginSuccess));
					if(loginSuccess){
						Log.d("A","Success");
						startMenu();
					}
					else{
						
					}
        			/////////////////////////////
        			
        		}
        	}
        });  
        
        
        // Login button listener
        btnRegister.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		startRegister();
        	}
        });
        
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
    }
    

    public void startMenu(){
    	this.startActivity(new Intent(getBaseContext(), Menu.class));
    }
    
    public void startRegister(){
    	this.startActivity(new Intent(getBaseContext(), Register.class));
    }
    
    
}