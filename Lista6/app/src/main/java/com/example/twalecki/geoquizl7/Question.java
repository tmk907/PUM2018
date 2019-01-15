package com.example.twalecki.geoquizl7;

public class Question {
    private String mQuestion;
    private boolean mAnswerTrue;
    private long id;

    public  Question(String mQuestion, boolean mAnswerTrue){
        this.mQuestion = mQuestion;
        this.mAnswerTrue = mAnswerTrue;
    }

    public  Question(long id, String mQuestion, boolean mAnswerTrue){
        this.mQuestion = mQuestion;
        this.mAnswerTrue = mAnswerTrue;
        this.id = id;
    }

    public String getQuestion(){
        return mQuestion;
    }

    public void setQuestion(String mQuestion){
        this.mQuestion = mQuestion;
    }

    public boolean isAnswerTrue(){
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
