package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int REQUEST_CODE_HINT = 1;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button mNextButton;
    private Button mCheatButton;
    private Button mHintButton;
    private TextView mQuestionTextView;
    private TextView mTotalMarksTextView;
    private TextView mQuestionCompletedTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, R.string.hint_australia, true, false, false, false), // NEW
            new Question(R.string.question_oceans, R.string.hint_oceans, true, false, false, false), // NEW
            new Question(R.string.question_mideast, R.string.hint_mideast, false, false, false, false), // NEW
            new Question(R.string.question_africa, R.string.hint_africa, false, false, false, false), // NEW
            new Question(R.string.question_americas, R.string.hint_americas, true, false, false, false), // NEW
            new Question(R.string.question_asia, R.string.hint_asia, true, false, false, false), // NEW
    };

    private int mCurrentIndex = 0;
    private int mTotalMarks = 0;
    private int mCompleteNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mCurrentIndex - 1;
                if (mCurrentIndex == -1) {
                    mCurrentIndex = mQuestionBank.length - 1;
                }
                updateQuestion();
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                //mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mHintButton = (Button) findViewById(R.id.hint_button);
        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int questionHintId =  mQuestionBank[mCurrentIndex].getTextHintId();
                Intent intent = HintActivity.newIntent(QuizActivity.this, questionHintId);
                startActivityForResult(intent, REQUEST_CODE_HINT);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mQuestionBank[mCurrentIndex].setUserCheated(CheatActivity.wasAnswerShown(data)); // NRW
        }

        if (requestCode == REQUEST_CODE_HINT) {
            if (data == null) {
                return;
            }
            mQuestionBank[mCurrentIndex].setUserHinted(HintActivity.wasHintShown(data)); // NEW
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mQuestionBank[mCurrentIndex].didUserCompleted()) {
            messageResId = R.string.complete_toast;
        } else {
            if (mQuestionBank[mCurrentIndex].didUserCheated()) {
                messageResId = R.string.judgment_toast;
            } else {
                if (userPressedTrue == answerIsTrue) {
                    if (mQuestionBank[mCurrentIndex].didUserHinted()) {
                        messageResId = R.string.correct_hinted_toast;
                        mTotalMarks = mTotalMarks + 1;
                    } else {
                        messageResId = R.string.correct_unhinted_toast;
                        mTotalMarks = mTotalMarks + 2;
                    }

                } else {
                    if (mQuestionBank[mCurrentIndex].didUserHinted()) {
                        messageResId = R.string.incorrect_hinted_toast;
                        mTotalMarks = mTotalMarks - 2;
                    } else {
                        messageResId = R.string.incorrect_unhinted_toast;
                        mTotalMarks = mTotalMarks - 1;
                    }

                }
                mQuestionBank[mCurrentIndex].setUserCompleted(true);
                mCompleteNumber++;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();

        mTotalMarksTextView = (TextView) findViewById(R.id.total_marks);
        mTotalMarksTextView.setText(Integer.toString(mTotalMarks));
        mQuestionCompletedTextView = (TextView) findViewById(R.id.questions_completed);
        mQuestionCompletedTextView.setText(Integer.toString(mCompleteNumber));

    }
}
