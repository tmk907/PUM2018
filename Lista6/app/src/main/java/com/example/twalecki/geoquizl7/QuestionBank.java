package com.example.twalecki.geoquizl7;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private QuestionBank() {
        questions = new ArrayList<Question>();
        questions.add(new Question("Czy Warszawa jest stolicą Polski?", true));
        questions.add(new Question("Czy Opole jest stolicą Dolnego Śląska?", false));
        questions.add(new Question("Czy Śnieżka to najwyższy szczyt Karkonoszy?", true));
        questions.add(new Question("Czy Wisła jest najdłuższą rzeką Polski?", true));
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
