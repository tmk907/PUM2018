package com.example.twalecki.geoquizl6;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private QuestionBank() {
        questions = new ArrayList<Question>();
        questions.add(new Question(R.string.question_stolica_polski, true));
        questions.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        questions.add(new Question(R.string.question_sniezka, true));
        questions.add(new Question(R.string.question_wisla, true));
    }

    private static QuestionBank instance;

    public static QuestionBank getInstance(){
        if (instance == null){
            instance = new QuestionBank();
        }
        return instance;
    }



    private List<Question> questions;

    public Question getQuestion(int index){
        return questions.get(index);
    }

    public List<Question> getQuestions(){
        return questions;
    }

    public int size(){
        return questions.size();
    }
}
