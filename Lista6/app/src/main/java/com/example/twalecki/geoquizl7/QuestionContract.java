package com.example.twalecki.geoquizl7;

import android.provider.BaseColumns;

public class QuestionContract {
    private QuestionContract() {}

    /* Inner class that defines the table contents */
    public static class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_ANSWER = "answer";
    }

}
