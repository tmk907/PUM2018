package com.example.twalecki.geoquizl4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
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
    private boolean[] givenAnswers;

    private Toast toastTrue;
    private Toast toastFalse;

    private final String CHANNEL_ID = "CHANNEL_IDCHANNEL_IDCHANNEL_ID";

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
                showNextQuestion();
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
                showNextQuestion();
            }
        });
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestion();
            }
        });

        points = 0;
        givenAnswers = new boolean[mQuestionBank.length];

        Context context = getApplicationContext();
        CharSequence textTrue = "Dobra odpowiedź!";
        CharSequence textFalse = "Zła odpowiedź!";

        int duration = Toast.LENGTH_SHORT;

        toastTrue = Toast.makeText(context, textTrue, duration);
        toastTrue.setGravity(Gravity.TOP,0,0);
        toastFalse = Toast.makeText(context, textFalse, duration);
        toastFalse.setGravity(Gravity.TOP,0,0);

        createNotificationChannel();
    }

    private void showNextQuestion() {
        mCurrentIndex++;
        if (mCurrentIndex == mQuestionBank.length) {
            mCurrentIndex = 0;
        }
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
    }

    private void showPreviousQuestion(){
        mCurrentIndex--;
        if (mCurrentIndex == -1){
            mCurrentIndex = mQuestionBank.length-1;
        }
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean isAnswerGiven = givenAnswers[mCurrentIndex];
        if (!isAnswerGiven) {
            if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressedTrue) {
                points++;
                mPointsTextView.setText(Integer.toString(points));
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
