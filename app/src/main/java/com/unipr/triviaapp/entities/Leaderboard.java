package com.unipr.triviaapp.entities;

public class Leaderboard implements Comparable<Leaderboard> {

    private Integer id;
    private String fullName;
    private int highScore;


    public Leaderboard(){

    }
    public Leaderboard( String user, int score) {
        this.fullName = user;

        this.highScore = score;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }


    @Override
    public int compareTo(Leaderboard o) {
        if(o.getHighScore() > this.getHighScore()){
            return 1;
        }
        else if (o.getHighScore() < this.getHighScore()) {
            return -1;
        }

        return 0;
    }
}
