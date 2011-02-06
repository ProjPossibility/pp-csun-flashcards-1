package edu.csun.ss12.flashcards;

import android.app.Activity;
import android.os.Bundle;

public class Flashcard  {

	private String mBack;
	/**
	 * @return the mBack
	 */
	public String getmBack() {
		return mBack;
	}

	/**
	 * @param mBack the mBack to set
	 */
	public void setmBack(String mBack) {
		this.mBack = mBack;
	}

	/**
	 * @return the mFront
	 */
	public String getmFront() {
		return mFront;
	}

	/**
	 * @param mFront the mFront to set
	 */
	public void setmFront(String mFront) {
		this.mFront = mFront;
	}

	private String mFront;
	
	public Flashcard(String front, String back){
		mFront = front;
		mBack = back;
	}

	

}
