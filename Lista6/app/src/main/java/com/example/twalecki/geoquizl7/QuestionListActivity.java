package com.example.twalecki.geoquizl7;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    private QuestionBank mQuestionBank;

    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;

    private QuestionDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        db = new QuestionDatabase(new GeoQuizDbHelper(this));

        mRecyclerView = (RecyclerView) findViewById(R.id.questionsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateList();
        //registerForContextMenu(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.question_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add:
                //you can write directly here or call a function which is more clear!
                AddNewQuestion();
                return true;
            case R.id.menu_quit:
                QuitApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Select The Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.mEditText:

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void AddNewQuestion(){
        Intent intent = EditQuestionActivity.newIntent(this,-1,"",false);
        startActivity(intent);
    }

    private void QuitApp(){
        finishAffinity();
    }

    private void updateList(){
        mAdapter = new QuestionAdapter(db.GetAllQuestions());
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
            mTitleTextView.setText(mQuestion.getQuestion());
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
