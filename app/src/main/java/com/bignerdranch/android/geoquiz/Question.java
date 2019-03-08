package com.bignerdranch.android.geoquiz;

public class Question {

    private int mTextResId;
    private int mTextHintId;  // NEW
    private boolean mAnswerTrue;
    private boolean mUserCheated; // NEW
    private boolean mUserHinted; // NEW
    private boolean mUserCompleted; // NEW

    public Question(int textResId, int textHintId, boolean answerTrue, boolean userCheated, boolean userHinted, boolean userCompleted) { // NEW
        mTextResId = textResId;
        mTextHintId = textHintId;
        mAnswerTrue = answerTrue;
        mUserCheated = userCheated;
        mUserHinted = userHinted;
        mUserCompleted = userCompleted;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public  int getTextHintId() { return mTextHintId; }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean didUserCheated() { //NEW
        return mUserCheated;
    }

    public  void setUserCheated(boolean userCheated) { // NEW
        mUserCheated = userCheated;
    }

    public boolean didUserHinted() { //NEW
        return mUserHinted;
    }

    public  void setUserHinted(boolean userHinted) { // NEW
        mUserHinted = userHinted;
    }

    public boolean didUserCompleted() { //NEW
        return mUserCompleted;
    }

    public  void setUserCompleted(boolean userCompleted) { // NEW
        mUserCompleted = userCompleted;
    }
}
