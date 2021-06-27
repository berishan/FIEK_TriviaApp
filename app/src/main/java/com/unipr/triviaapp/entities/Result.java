package com.unipr.triviaapp.entities;

public class Result implements Comparable<Result>{

    private Integer id;
    private String user;
    private String numberOfQuestions;
    private String correctAnswers;
    private String totalPoints;
    private String date;
    private String difficulty;
    private String category;

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

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(String numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Result(Integer id, String user, String numberOfQuestions, String correctAnswers, String totalPoints, String date, String difficulty, String category) {
        this.id = id;
        this.user = user;
        this.numberOfQuestions = numberOfQuestions;
        this.correctAnswers = correctAnswers;
        this.totalPoints = totalPoints;
        this.date = date;
        this.difficulty = difficulty;
        this.category = category;
    }

    @Override
    public int compareTo(Result o) {
        return this.getDate().compareTo(o.getDate());
    }
}
