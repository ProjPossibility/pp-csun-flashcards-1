package edu.csun.ss12.flashcards;

import android.app.Activity;
import android.os.Bundle;

public class Flashcard  {

	private String mFlashcardId;
	
	private String mBack;
	private String mFront;
	
	public Flashcard(String front, String back, String id){
		mFront = front;
		mBack = back;
		mFlashcardId = id;
	}
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



	/**
	 * @return the mFlashcardId
	 */
	public String getmFlashcardId() {
		return mFlashcardId;
	}

	/**
	 * @param mFlashcardId the mFlashcardId to set
	 */
	public void setmFlashcardId(String mFlashcardId) {
		this.mFlashcardId = mFlashcardId;
	}

	

}
