package com.unipr.triviaapp.entities;

import java.util.List;

public class Quiz {

    private String userEmail;
    private Integer id;
    private String name;
    private List<Question> questions;
    private Integer numberOfQuestions;
    private String dateCreated;

    public Quiz() {

    }

    public Quiz(String userEmail, Integer id, String name, List<Question> questions, Integer numberOfQuestions, String dateCreated) {
        this.userEmail = userEmail;
        this.id = id;
        this.name = name;
        this.questions = questions;
        this.numberOfQuestions = numberOfQuestions;
        this.dateCreated = dateCreated;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getNumberOfQuestions() {
        return this.numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
