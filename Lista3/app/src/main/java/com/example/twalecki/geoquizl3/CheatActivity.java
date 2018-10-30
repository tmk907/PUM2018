package com.example.twalecki.geoquizl3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private TextView mCorrectAnswerTextView;
    private Button mCheatButton;

    private boolean isCheatUsed;
    private boolean correctAnswer;

    private final static String CORRECT_ANSWER_MESSAGE = "correctAnswer";
    private final static String CHEAT_USED_MESSAGE = "CHEAT_USED_MESSAGE";

    private final String CHEAT_USED_STATE = "CHEAT_USED_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        correctAnswer = getIntent().getBooleanExtra(CORRECT_ANSWER_MESSAGE,false);
        isCheatUsed=false;

        mCorrectAnswerTextView = (TextView) findViewById(R.id.mCorrectAswerTextView);
        mCheatButton = (Button) findViewById(R.id.mCheatButton);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
            }
        });
    }

    private void showAnswer(){
        isCheatUsed=true;
        mCorrectAnswerTextView.setText("Poprawna odpowiedź to: " + Boolean.toString(correctAnswer).toUpperCase());
        Intent data = new Intent();
        data.putExtra(CHEAT_USED_MESSAGE,true);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(CHEAT_USED_STATE,isCheatUsed);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isCheatUsed = savedInstanceState.getBoolean(CHEAT_USED_STATE);
        if (isCheatUsed){
            showAnswer();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    public static Intent newIntent(Context packageContext, boolean correctAnswer){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(CORRECT_ANSWER_MESSAGE, correctAnswer);
        return intent;
    }

    public static boolean wasAnswerShown(Intent intent){
        return intent.getBooleanExtra(CHEAT_USED_MESSAGE,false);
    }
}
