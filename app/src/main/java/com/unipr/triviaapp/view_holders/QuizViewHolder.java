package com.unipr.triviaapp.view_holders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unipr.triviaapp.R;

public class QuizViewHolder {

    TextView tvQuizName, tvDateCreated, tvNumberOfQuestions;
    Button btnDeleteQuiz, btnStartQuiz;

    public QuizViewHolder(View view) {
        this.tvQuizName = view.findViewById(R.id.tvQuizName);
        this.tvDateCreated = view.findViewById(R.id.tvDateCreated);
        this.tvNumberOfQuestions = view.findViewById(R.id.tvQuestions);
        this.btnDeleteQuiz = view.findViewById(R.id.btnDeleteQuiz);
        this.btnStartQuiz = view.findViewById(R.id.btnPlayQuiz);
    }

    public TextView getTvQuizName() {
        return tvQuizName;
    }

    public void setTvQuizName(TextView tvQuizName) {
        this.tvQuizName = tvQuizName;
    }

    public TextView getTvDateCreated() {
        return tvDateCreated;
    }

    public void setTvDateCreated(TextView tvDateCreated) {
        this.tvDateCreated = tvDateCreated;
    }

    public TextView getTvNumberOfQuestions() {
        return tvNumberOfQuestions;
    }

    public void setTvNumberOfQuestions(TextView tvNumberOfQuestions) {
        this.tvNumberOfQuestions = tvNumberOfQuestions;
    }

    public Button getBtnDeleteQuiz() {
        return btnDeleteQuiz;
    }

    public void setBtnDeleteQuiz(Button btnDeleteQuiz) {
        this.btnDeleteQuiz = btnDeleteQuiz;
    }

    public Button getBtnStartQuiz() {
        return btnStartQuiz;
    }

    public void setBtnStartQuiz(Button btnStartQuiz) {
        this.btnStartQuiz = btnStartQuiz;
    }
}
