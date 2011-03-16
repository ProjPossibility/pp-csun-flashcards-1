package edu.csun.ss12.flashcards;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MySQL_Connection {
/** Called when the activity is first created. */



public  String[] getLogin(String user_name) {
	String returnString = "http://calqlus.org/ss12/android_Login.php";
   String[] returnArray = new String[3]; 
   InputStream is = null;
    
   String result = "";
    // send
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("user_name",user_name));

    //http post
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }

    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
    //parse json data
    try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("log_tag","id: "+json_data.getInt("user_id")+
                            ", name: "+json_data.getString("user_name")+
                            ", sex: "+json_data.getString("password")
                    );
                    returnArray[0] = Integer.toString(json_data.getInt("user_id"));
                    returnArray[1] = json_data.getString("user_name");
                    returnArray[2] = json_data.getString("password");
                    //Get an output to the screen
                    returnString += "\n\t" + jArray.getJSONObject(i); 
            }
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }
    return returnArray; 
}    

public  boolean register(String user_name, String password) {
	String returnString = "http://calqlus.org/ss12/android_register.php";   
   InputStream is = null;
   String return_value=""; 
   String result = "";
    // send
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("user_name",user_name));
    nameValuePairs.add(new BasicNameValuePair("password",password));
    //http post
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }

    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
    //parse json data
    try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);                    
                    return_value = json_data.getString("result");
                    
                    //Get an output to the screen
                    returnString += "\n\t" + jArray.getJSONObject(i); 
            }
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }
    if(return_value.compareTo("T")==0){
    	return true;
    }
    else{
    	return false;
    }
}     	
public  JSONArray getFlashCard(int user_id) {
   String returnString ="http://calqlus.org/ss12/android_browse.php";
   InputStream is = null;
    
   String result = "";
    // send
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    Log.e("asd",Integer.toString(user_id));
    nameValuePairs.add(new BasicNameValuePair("user_id",Integer.toString(user_id)));

    //http post
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }

    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
    //parse json data
    JSONArray jArray = null;
    try{
            jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("log_tag","id: "+json_data.getString("flashcard_id")+
                            ", front: "+json_data.getString("front")+
                            ", back: "+json_data.getString("back")
                    );
                    //Get an output to the screen
                    returnString += "\n\t" + jArray.getJSONObject(i); 
            }
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }
    return jArray; 
}    
public  JSONArray getFrontandBack(int flashcard_id) {
	   String returnString ="http://calqlus.org/ss12/android_getFlashCard.php";
	   InputStream is = null;
	    
	   String result = "";
	    // send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    Log.e("asd",Integer.toString(flashcard_id));
	    nameValuePairs.add(new BasicNameValuePair("flashcard_id",Integer.toString(flashcard_id)));

	    //http post
	    try{
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(returnString);
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();

	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	    }

	    //convert response to string
	    try{
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            }
	            is.close();
	            result=sb.toString();
	    }catch(Exception e){
	            Log.e("log_tag", "Error converting result "+e.toString());
	    }
	    //parse json data
	    JSONArray jArray = null;
	    try{
	            jArray = new JSONArray(result);
	            for(int i=0;i<jArray.length();i++){
	                    JSONObject json_data = jArray.getJSONObject(i);
	                    Log.i("log_tag","back: "+json_data.getString("back")+
	                            ", front: "+json_data.getString("front")
	                    );
	                    //Get an output to the screen
	                    returnString += "\n\t" + jArray.getJSONObject(i); 
	            }
	    }catch(JSONException e){
	            Log.e("log_tag", "Error parsing data "+e.toString());
	    }
	    return jArray; 
	}    

public  boolean create(int user_id,String group_name,  String front, String back) {
	System.out.println(group_name);
	String returnString = "http://calqlus.org/ss12/android_Create.php";   
   InputStream is = null;
   String return_value=""; 
   String result = "";
    // send
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("user_id",Integer.toString(user_id)));
    nameValuePairs.add(new BasicNameValuePair("name",group_name));
    nameValuePairs.add(new BasicNameValuePair("front",front));
    nameValuePairs.add(new BasicNameValuePair("back", back));
    //http post
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }

    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
    //parse json data
    try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);                    
                    return_value = json_data.getString("result");
                    
                    //Get an output to the screen
                    returnString += "\n\t" + jArray.getJSONObject(i); 
            }
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }
    if(return_value.compareTo("T")==0){
    	return true;
    }
    else{
    	return false;
    }
}
public  boolean modify(String flashcard_id,  String front, String back) {
	String returnString = "http://calqlus.org/ss12/android_Modify.php";   
   InputStream is = null;
   String return_value=""; 
   String result = "";
    // send
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("flashcard_id",flashcard_id));
    nameValuePairs.add(new BasicNameValuePair("front",front));
    nameValuePairs.add(new BasicNameValuePair("back", back));
    //http post
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }

    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
    //parse json data
    try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);                    
                    return_value = json_data.getString("result");
                    
                    //Get an output to the screen
                    returnString += "\n\t" + jArray.getJSONObject(i); 
            }
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }
    if(return_value.compareTo("T")==0){
    	return true;
    }
    else{
    	return false;
    }
} 
public  boolean delete(int flashcard_id) {	
	String returnString = "http://calqlus.org/ss12/android_Delete.php";   
   InputStream is = null;
   String return_value=""; 
   String result = "";
    // send
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("flashcard_id",Integer.toString(flashcard_id)));
    //http post
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }

    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
    //parse json data
    try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);                    
                    return_value = json_data.getString("result");
                    
                    //Get an output to the screen
                    returnString += "\n\t" + jArray.getJSONObject(i); 
            }
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }
    if(return_value.compareTo("T")==0){
    	return true;
    }
    else{
    	return false;
    }
}
}
