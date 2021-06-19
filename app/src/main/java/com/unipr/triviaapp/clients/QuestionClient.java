package com.unipr.triviaapp.clients;

import com.unipr.triviaapp.entities.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionClient {

    public static ArrayList<Question> getQuestions(int category, int difficulty){
        ArrayList<Question> arrayList = new ArrayList();
        arrayList.add(new Question(
                1,
                "What's my name?",
                "Deez",
                "Jeff",
                "Heisenberg",
                "Chicka chicka Slim Shady",
                3));
        arrayList.add(new Question(
                1,
                "What's my last name?",
                "Nuts",
                "Jeff",
                "Heisenberg",
                "Eminem",
                4));
        arrayList.add(new Question(
                1,
                "What's my age?",
                "1",
                "2",
                "3",
                "Chicka chicka Slim Shady",
                1));
        arrayList.add(new Question(
                1,
                "What's my name?",
                "Deez",
                "Jeff",
                "Heisenberg",
                "Chicka chicka Slim Shady",
                3));


        return arrayList;
    }
}
