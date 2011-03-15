package edu.csun.ss12.flashcards;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Instrumentation;

public class functions {
		public static String MD5(String password) throws NoSuchAlgorithmException{
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes()); 
	        byte byteData[] = md.digest(); 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        } 
	       return sb.toString();
		}
		public static String getSpeech(String speech){
			int index1 = speech.indexOf("displaystyle");
			if (index1!=-1){
				int index2 = speech.indexOf(" ", index1);
				int index3 = speech.indexOf("'", index1);
				String temp=speech.substring(index2, index3);
				if(temp!=null) {					
					int index4 = speech.indexOf("<img");
					int index5 = speech.indexOf("absmiddle>");
					String[] substrings = speech.split("<img");
					speech ="";
					String[] substrings1;
					if(substrings.length==2){
						speech = substrings[0];
						substrings1 = substrings[1].split("absmiddle>");
					}
					else{
						substrings1 = substrings[0].split("absmiddle>");
					}
					speech =speech + " " + temp;
					if(substrings1.length==2){
						speech = speech + " " + substrings1[1];						
					}
									}
			}
			return speech;
		}
		public static void InjectKeys(final int keyEventCode) {
	    	 new Thread(new Runnable() {
	    	  @Override
	    	  public void run() {
	    	   new Instrumentation().sendKeyDownUpSync(keyEventCode);
	    	  }
	    	 }).start();
	    	}
		
}