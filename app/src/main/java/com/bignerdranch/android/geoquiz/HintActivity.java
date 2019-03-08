package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HintActivity extends AppCompatActivity {

    private static final String EXTRA_HINT_IS_TRUE =
            "com.bignerdranch.android.geoquiz.extra_hint_is_true";
    private static final String EXTRA_HINT_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";

    private int mHintId;

    private TextView mHintTextView;
    private Button mShowHintButton;
    private Button mExitButton;

    public static Intent newIntent(Context packageContext, int questionHintId) {
        Intent intent = new Intent(packageContext, HintActivity.class);
        intent.putExtra(EXTRA_HINT_IS_TRUE, questionHintId);
        return intent;
    }

    public static boolean wasHintShown(Intent result) {
        return result.getBooleanExtra(EXTRA_HINT_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        mHintId = getIntent().getIntExtra(EXTRA_HINT_IS_TRUE, 0);

        mHintTextView = (TextView) findViewById(R.id.hint_text_view);

        mShowHintButton = (Button) findViewById(R.id.show_hint_button);
        mShowHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHintTextView.setText(mHintId);
                setHintShownResult(true);
            }
        });

        mExitButton = (Button) findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setHintShownResult(boolean isHintShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_HINT_SHOWN, isHintShown);
        setResult(RESULT_OK, data);
    }
}
