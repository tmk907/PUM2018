package com.example.twalecki.geoquizl6;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private int mCurrentIndex;
    private QuestionBank mQuestionBank;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;
    private TextView mPointsTextView;
    private Button mCheatButton;
    private TextView mAPITextView;
    private Button mQuestionListButton;

    private int points;
    private boolean[] givenAnswers;
    private boolean[] usedCheats;

    private final int MAX_TOKENS = 3;
    private int tokensUsed;

    private Toast toastTrue;
    private Toast toastFalse;

    private final String CHANNEL_ID = "CHANNEL_IDCHANNEL_IDCHANNEL_ID";

    private final String POINTS_STATE = "POINTS_STATE";
    private final String GIVEN_ANSWERS_STATE = "GIVEN_ANSWERS_STATE";
    private final String CURRENT_INDEX_STATE = "CURRENT_INDEX_STATE";
    private final String USED_CHEATS_STATE = "USED_CHEATS_STATE";
    private final String TOKENS_USED_STATE = "TOKENS_USED_STATE";

    public static final int CHEAT_CODE_REQUEST = 0;
    private final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mCurrentIndex = 0;

        mTrueButton = (Button) findViewById(R.id.mTrueButton);
        mFalseButton = (Button) findViewById(R.id.mFalseButton);
        mNextButton = (ImageButton) findViewById(R.id.mNextButton);
        mPreviousButton = (ImageButton) findViewById(R.id.mPreviousButton);
        mQuestionTextView = (TextView) findViewById(R.id.mQuestionTextView);

        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showNextQuestion();
            }
        });
//        mPointsTextView = (TextView) findViewById(R.id.mPointsTextView);
//        mPointsTextView.setText(Integer.toString(points));
        mCheatButton = (Button) findViewById((R.id.mCheatButton));
        mQuestionListButton = (Button) findViewById(R.id.mQuestionListButton);

        mAPITextView = (TextView) findViewById(R.id.mApiTextView);
        mAPITextView.setText("Wersja Api: " + Build.VERSION.SDK_INT);

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
                showNextQuestion();
            }
        });
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestion();
            }
        });
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartCheatActivity();
            }
        });
        mQuestionListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartQuestionListActivity();
            }
        });

        points = 0;
        mQuestionBank = QuestionBank.getInstance();
        givenAnswers = new boolean[mQuestionBank.size()];
        usedCheats = new boolean[mQuestionBank.size()];
        tokensUsed=0;

        Context context = getApplicationContext();
        CharSequence textTrue = "Dobra odpowiedź!";
        CharSequence textFalse = "Zła odpowiedź!";

        int duration = Toast.LENGTH_SHORT;

        toastTrue = Toast.makeText(context, textTrue, duration);
        toastTrue.setGravity(Gravity.TOP,0,0);
        toastFalse = Toast.makeText(context, textFalse, duration);
        toastFalse.setGravity(Gravity.TOP,0,0);

        createNotificationChannel();
        updateQuestion();

        Log.d(TAG,"onCreate");
    }

    private void updateQuestion(){
        Log.d(TAG,"updateQuestion");
//        mPointsTextView.setText(Integer.toString(points));
        mQuestionTextView.setText(mQuestionBank.getQuestion(mCurrentIndex).getTextResId());
        if(givenAnswers[mCurrentIndex]){
            disableButtons();
        }
        else{
            enableButtons();
        }
    }

    private void updateCheatButtonVisibility(){
        if (tokensUsed == MAX_TOKENS){
            mCheatButton.setVisibility(View.INVISIBLE);
        }
        else{
            mCheatButton.setVisibility(View.VISIBLE);
        }
    }

    private void showNextQuestion() {
        mCurrentIndex++;
        if (mCurrentIndex == mQuestionBank.size()) {
            mCurrentIndex = 0;
        }
        updateQuestion();
    }

    private void showPreviousQuestion(){
        mCurrentIndex--;
        if (mCurrentIndex == -1){
            mCurrentIndex = mQuestionBank.size()-1;
        }
        updateQuestion();
    }

    private void disableButtons(){
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private void enableButtons(){
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == CHEAT_CODE_REQUEST
                && data != null){
            Log.d(TAG,"Is cheater");
            usedCheats[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
            tokensUsed++;
            updateCheatButtonVisibility();
            updateQuestion();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"onSaveInstanceState");
        outState.putInt(CURRENT_INDEX_STATE, mCurrentIndex);
        outState.putInt(POINTS_STATE,points);
        outState.putBooleanArray(GIVEN_ANSWERS_STATE,givenAnswers);
        outState.putBooleanArray(USED_CHEATS_STATE,usedCheats);
        outState.putInt(TOKENS_USED_STATE,tokensUsed);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG,"onRestoreInstanceState");

        mCurrentIndex = savedInstanceState.getInt(CURRENT_INDEX_STATE);
        points = savedInstanceState.getInt(POINTS_STATE);
        givenAnswers = savedInstanceState.getBooleanArray(GIVEN_ANSWERS_STATE);
        usedCheats = savedInstanceState.getBooleanArray(USED_CHEATS_STATE);
        tokensUsed = savedInstanceState.getInt(TOKENS_USED_STATE);

        updateCheatButtonVisibility();
        updateQuestion();
    }

    private void checkAnswer(boolean userPressedTrue) {
        disableButtons();
        boolean isAnswerGiven = givenAnswers[mCurrentIndex];
        if (!isAnswerGiven) {
            if (mQuestionBank.getQuestion(mCurrentIndex).isAnswerTrue() == userPressedTrue) {
                points++;
//                mPointsTextView.setText(Integer.toString(points));
                toastTrue.show();
            } else {
                toastFalse.show();
            }
        }
        givenAnswers[mCurrentIndex] = true;
        if (AreAllAnswersGiven()){
            ShowResultsNotification();
        }
    }

    private boolean AreAllAnswersGiven(){
        for(boolean x: givenAnswers){
            if (!x){
                return false;
            }
        }
        return true;
    }

    private void StartCheatActivity(){
        Intent intent = CheatActivity.newIntent(this, mQuestionBank.getQuestion(mCurrentIndex).isAnswerTrue());
        startActivityForResult(intent, CHEAT_CODE_REQUEST);
    }

    private void StartQuestionListActivity(){
        Intent intent = QuestionListActivity.newIntent(this);
        startActivity(intent);
    }

    private void ShowResultsNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Wyniki quizu:")
                .setContentText("Poprawnych odpowiedzi: " + Integer.toString(points))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = mBuilder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
