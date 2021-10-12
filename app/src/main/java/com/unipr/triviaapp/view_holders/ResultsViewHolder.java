package com.unipr.triviaapp.view_holders;

import android.view.View;
import android.widget.TextView;

import com.unipr.triviaapp.R;

public class ResultsViewHolder {


    TextView dateTaken, score, category, difficulty, questions;

    public ResultsViewHolder(View view) {
        score = view.findViewById(R.id.scoresTv);
        difficulty = view.findViewById(R.id.tvDifficulty);
        questions = view.findViewById(R.id.questionsTv);
        category = view.findViewById(R.id.tvCategory);
        dateTaken = view.findViewById(R.id.tvDateTaken);
    }

    public TextView getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(TextView dateTaken) {
        this.dateTaken = dateTaken;
    }

    public TextView getScore() {
        return score;
    }

    public void setScore(TextView score) {
        this.score = score;
    }

    public TextView getCategory() {
        return category;
    }

    public void setCategory(TextView category) {
        this.category = category;
    }

    public TextView getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(TextView difficulty) {
        this.difficulty = difficulty;
    }

    public TextView getQuestions() {
        return questions;
    }

    public void setQuestions(TextView questions) {
        this.questions = questions;
    }
}
