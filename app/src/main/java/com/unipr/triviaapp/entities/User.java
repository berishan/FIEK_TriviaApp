package com.unipr.triviaapp.entities;

public class User {

    private String name, lastName, email;
    private int highScore;

    public User() {

    }

    public User(String name, String lastName, String email, int highScore) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
