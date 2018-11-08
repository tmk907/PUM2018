package com.example.twalecki.geoquizl5;

import android.content.Context;

import java.util.List;

public class QuestionDataSource {
    // statyczne pole o typu tej klasy. Dziwne prawda? ;)
    private static QuestionDataSource sQuestionDataSource;

    private List<Question> mQuestions;

    public static QuestionDataSource get(Context context) {
        if (sQuestionDataSource == null)
        {
            sQuestionDataSource = new QuestionDataSource(context);
        }
        return sQuestionDataSource;
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    private QuestionDataSource(Context context) {
        mQuestions = QuestionBank.getInstance().getQuestions();
    }
}
