package com.example.twalecki.geoquizl5;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    private QuestionBank mQuestionBank;

    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.questionsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateList();
    }

    private void updateList(){
        QuestionDataSource dataSource = QuestionDataSource.get(this);
        mAdapter = new QuestionAdapter(dataSource.getQuestions());
        mRecyclerView.setAdapter(mAdapter);
    }

    private class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private Question mQuestion;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.question_layout, parent, false));
            // reference to item fragment
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.questionContent);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(Question question) {
            mQuestion = question;
            mTitleTextView.setText(mQuestion.getTextResId());
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{
        private List<Question> mQuestions;

        public QuestionAdapter(List<Question> mList){
            mQuestions = mList;
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(QuestionListActivity.this);
            return new QuestionHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder questionHolder, int position) {
            Question question = mQuestions.get(position);
            questionHolder.bind(question);
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }
    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, QuestionListActivity.class);
        return intent;
    }
}
