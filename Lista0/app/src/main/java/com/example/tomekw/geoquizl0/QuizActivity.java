package com.example.tomekw.geoquizl0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private int mCurrentIndex;
    private Question[] mQuestionBank;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private TextView mPointsTextView;

    private int points;
    private boolean canAddPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mCurrentIndex = 0;
        mQuestionBank = new Question[]{
                new Question(R.string.question_stolica_polski,true),
                new Question(R.string.question_stolica_dolnego_slaska,false),
                new Question(R.string.question_sniezka,true),
                new Question(R.string.question_wisla,true)
        };

        mTrueButton = (Button) findViewById(R.id.mTrueButton);
        mFalseButton = (Button) findViewById(R.id.mFalseButton);
        mNextButton = (Button) findViewById(R.id.mNextButton);
        mQuestionTextView = (TextView) findViewById(R.id.mQuestionTextView);
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        mPointsTextView = (TextView) findViewById(R.id.mPointsTextView);
        mPointsTextView.setText(Integer.toString(points));

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer();
            }
        });

        points = 0;
        canAddPoints = true;
    }

    private void updateAnswer(){
        mCurrentIndex++;
        if (mCurrentIndex == mQuestionBank.length){
            mCurrentIndex = 0;
        }
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        canAddPoints = true;
    }

    private void checkAnswer(boolean userPressedTrue){
        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressedTrue){
            if (canAddPoints) {
                points++;
            }
            mPointsTextView.setText(Integer.toString(points));
        }
        canAddPoints = false;
    }
}
