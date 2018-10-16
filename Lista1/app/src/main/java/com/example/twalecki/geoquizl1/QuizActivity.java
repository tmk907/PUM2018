package com.example.twalecki.geoquizl1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private int mCurrentIndex;
    private Question[] mQuestionBank;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;
    private TextView mPointsTextView;

    private int points;
    private boolean canAddPoints;

    private Toast toastTrue;
    private Toast toastFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mCurrentIndex = 0;
        mQuestionBank = new Question[]{
                new Question(R.string.question_stolica_polski, true),
                new Question(R.string.question_stolica_dolnego_slaska, false),
                new Question(R.string.question_sniezka, true),
                new Question(R.string.question_wisla, true)
        };

        mTrueButton = (Button) findViewById(R.id.mTrueButton);
        mFalseButton = (Button) findViewById(R.id.mFalseButton);
        mNextButton = (ImageButton) findViewById(R.id.mNextButton);
        mPreviousButton = (ImageButton) findViewById(R.id.mPreviousButton);
        mQuestionTextView = (TextView) findViewById(R.id.mQuestionTextView);
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateAnswer();
            }
        });
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
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestion();
            }
        });

        points = 0;
        canAddPoints = true;

        Context context = getApplicationContext();
        CharSequence textTrue = "Dobra odpowiedź!";
        CharSequence textFalse = "Zła odpowiedź!";

        int duration = Toast.LENGTH_SHORT;

        toastTrue = Toast.makeText(context, textTrue, duration);
        toastTrue.setGravity(Gravity.TOP,0,0);
        toastFalse = Toast.makeText(context, textFalse, duration);
        toastFalse.setGravity(Gravity.TOP,0,0);
    }

    private void updateAnswer() {
        mCurrentIndex++;
        if (mCurrentIndex == mQuestionBank.length) {
            mCurrentIndex = 0;
        }
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        canAddPoints = true;
    }

    private void showPreviousQuestion(){
        mCurrentIndex--;
        if (mCurrentIndex == -1){
            mCurrentIndex = mQuestionBank.length-1;
        }
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
    }

    private void checkAnswer(boolean userPressedTrue) {
        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressedTrue) {
            if (canAddPoints) {
                points++;
            }
            mPointsTextView.setText(Integer.toString(points));
            toastTrue.show();
        }
        else{
            toastFalse.show();
        }
        canAddPoints = false;
    }
}
