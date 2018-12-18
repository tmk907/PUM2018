package com.example.twalecki.geoquizl7;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private TextView mPointsTextView;
    private TextView mNumberOfQuestionsTextView;
    private TextView mTokenUsedTextView;

    private Button mShareButton;
    private Button mTakePictureButton;

    private ImageView mImageView;

    private final static String POINTS_MESSAGE = "POINTS_MESSAGE";
    private final static String NUMBER_QUESTIONS_MESSAGE = "NUMBER_QUESTIONS_MESSAGE";
    private final static String TOKEN_USED_MESSAGE = "TOKEN_USED_MESSAGE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mPointsTextView = (TextView) findViewById(R.id.mPointsTextView);
        mNumberOfQuestionsTextView = (TextView) findViewById(R.id.mNumberOfQuestionsTextView);
        mTokenUsedTextView = (TextView) findViewById(R.id.mTokenUsedTextView);

        mPointsTextView.setText(Integer.toString(getIntent().getIntExtra(POINTS_MESSAGE, 0)));
        mNumberOfQuestionsTextView.setText(Integer.toString(getIntent().getIntExtra(NUMBER_QUESTIONS_MESSAGE, 0)));
        mTokenUsedTextView.setText(Integer.toString(getIntent().getIntExtra(TOKEN_USED_MESSAGE, 0)));

        mShareButton = (Button) findViewById(R.id.mShareButton);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareResult();
            }
        });

        mTakePictureButton = (Button) findViewById(R.id.takePictureButton);
        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePicture();
            }
        });

        mImageView = (ImageView) findViewById(R.id.image_view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(this, QuestionListActivity.class));
        finish();
        return true;
    }


    private void ShareResult(){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "W GeoQuiz zdobyłem " + mPointsTextView.getText() + " pkt!");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    private void TakePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            mImageView.setVisibility(1);
        }
    }

    public static Intent newIntent(Context packageContext, int points, int numberOfQuestions, int tokenUsed){
        Intent intent = new Intent(packageContext, SummaryActivity.class);
        intent.putExtra(POINTS_MESSAGE, points);
        intent.putExtra(NUMBER_QUESTIONS_MESSAGE, numberOfQuestions);
        intent.putExtra(TOKEN_USED_MESSAGE, tokenUsed);
        return intent;
    }
}
