package com.example.twalecki.geoquizl7;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class EditQuestionActivity extends AppCompatActivity {

    private String question;
    private Boolean answer;
    private long id;

    private EditText mQuestionTextView;
    private Switch mAnswerSwitch;
    private Button mSaveButton;


    private final static String QUESTION_MESSAGE = "QUESTION_MESSAGE";
    private final static String ANSWER_MESSAGE = "ANSWER_MESSAGE";
    private final static String ID_MESSAGE = "ID_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        id = getIntent().getLongExtra(ID_MESSAGE,-1);
        question = getIntent().getStringExtra(QUESTION_MESSAGE);
        answer = getIntent().getBooleanExtra(ANSWER_MESSAGE,false);

        mQuestionTextView = (EditText) findViewById(R.id.mEditText);
        mAnswerSwitch = (Switch) findViewById(R.id.mAnswerSwitch);
        mSaveButton = (Button) findViewById(R.id.mSaveButton);

        mQuestionTextView.setText(question);
        mAnswerSwitch.setChecked(answer);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveQuestion();
            }
        });
    }

    private void SaveQuestion(){
        if (id==-1){
            GeoQuizDbHelper mDbHelper = new GeoQuizDbHelper(this);
            QuestionDatabase db = new QuestionDatabase(mDbHelper);
            db.AddQuestion(mQuestionTextView.getText().toString(), mAnswerSwitch.isChecked());

        }
        else{

        }
        finish();
    }

    public static Intent newIntent(Context packageContext, long id, String question, boolean answer){
        Intent intent = new Intent(packageContext, EditQuestionActivity.class);
        intent.putExtra(ID_MESSAGE, id);
        intent.putExtra(QUESTION_MESSAGE, question);
        intent.putExtra(ANSWER_MESSAGE, answer);
        return intent;
    }
}
