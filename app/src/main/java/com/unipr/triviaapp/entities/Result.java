package com.unipr.triviaapp.entities;

public class Result {

    private Integer id;
    private String user;
    private Integer numberOfQuestions;
    private Integer correctAnswers;
    private Integer totalPoints;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Result(Integer id, String user, Integer numberOfQuestions, Integer correctAnswers, Integer totalPoints, String date) {
        this.id = id;
        this.user = user;
        this.numberOfQuestions = numberOfQuestions;
        this.correctAnswers = correctAnswers;
        this.totalPoints = totalPoints;
        this.date = date;
    }
}
