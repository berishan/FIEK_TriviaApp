package com.unipr.triviaapp.entities;

public class Leaderboard {

    private Integer id;
    private String user;
    private String email;
    private String score;
    private String difficulty;
    private String category;

    public Leaderboard(Integer id, String user, String email, String score, String difficulty, String category) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.score = score;
        this.difficulty = difficulty;
        this.category = category;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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
}
