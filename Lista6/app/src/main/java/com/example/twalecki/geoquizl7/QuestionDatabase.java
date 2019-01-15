package com.example.twalecki.geoquizl7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class QuestionDatabase {

    private GeoQuizDbHelper dbHelper;

    public QuestionDatabase(GeoQuizDbHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public List<Question> GetAllQuestions(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                QuestionContract.QuestionEntry.COLUMN_NAME_QUESTION,
                QuestionContract.QuestionEntry.COLUMN_NAME_ANSWER
        };


        Cursor cursor = db.query(
                QuestionContract.QuestionEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Question> questions = new ArrayList<Question>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry._ID));
            String question = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry.COLUMN_NAME_QUESTION));
            boolean answer = cursor.getInt(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry.COLUMN_NAME_QUESTION)) == 1;
            questions.add(new Question(itemId, question, answer));
        }
        cursor.close();
        return questions;
    }

    public long AddQuestion(String question, boolean answer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuestionContract.QuestionEntry.COLUMN_NAME_QUESTION, question);
        values.put(QuestionContract.QuestionEntry.COLUMN_NAME_ANSWER, answer);

        long newRowId = db.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    public void UpdateQuestion(long id, String question, boolean answer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionContract.QuestionEntry.COLUMN_NAME_QUESTION, question);
        contentValues.put(QuestionContract.QuestionEntry.COLUMN_NAME_ANSWER, answer);
        db.update(QuestionContract.QuestionEntry.TABLE_NAME, contentValues,
                QuestionContract.QuestionEntry._ID + " = " + id,
                null);
    }

    public void DeleteQuestion(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
// Define 'where' part of query.
        String selection = QuestionContract.QuestionEntry._ID + " = ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
        int deletedRows = db.delete(QuestionContract.QuestionEntry.TABLE_NAME, selection, selectionArgs);
    }
}
