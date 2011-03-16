package edu.csun.ss12.flashcards;

import android.app.Activity;
import android.os.Bundle;

public class Flashcard  {

	private String mFlashcardId;
	private String mGroup;
	private String mBack;
	private String mFront;
	private int mOriginal_Index = -1 ; //Use for Searching 
	/**
	 * @return the mGroup
	 */
	public String getmGroup() {
		return mGroup;
	}
	/**
	 * @param mGroup the mGroup to set
	 */
	public void setmGroup(String mGroup) {
		this.mGroup = mGroup;
	}
	public Flashcard(String front, String back, String id, String group){
		mFront = front;
		mBack = back;
		mFlashcardId = id;
		mGroup = group;
	}
	
	public Flashcard(String front, String back, String id){
		mFront = front;
		mBack = back;
		mFlashcardId = id;
	}
	
	public Flashcard(String front, String back, String id, int original_id){
		mFront = front;
		mBack = back;
		mFlashcardId = id;
		mOriginal_Index = original_id;
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
	/** 
	 * @param mOriginal_Index
	 */
	public void setmOriginal_Index(int mOriginal_Index) {
		this.mOriginal_Index = mOriginal_Index;
	}
	/** 
	 * @return mOriginal_Index
	 */
	public int getmOriginal_Index() {
		return mOriginal_Index;
	}

	

}
