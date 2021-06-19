package com.unipr.triviaapp.view_holders;

import android.view.View;
import android.widget.TextView;

import com.unipr.triviaapp.R;

public class ResultsViewHolder {
    TextView usersEmail, correctAnswers, numberOfPoints, numberOfQuestions, dateTaken;

    public TextView getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(TextView usersEmail) {
        this.usersEmail = usersEmail;
    }

    public TextView getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(TextView correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public TextView getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(TextView numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public TextView getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(TextView numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public TextView getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(TextView dateTaken) {
        this.dateTaken = dateTaken;
    }

    public ResultsViewHolder(View view) {
        usersEmail = view.findViewById(R.id.tvUser);
        correctAnswers = view.findViewById(R.id.tvCorrectAnswers);
        numberOfPoints = view.findViewById(R.id.tvNumberOfPoints);
        numberOfQuestions = view.findViewById(R.id.etNumOfQuestions);
        dateTaken = view.findViewById(R.id.dateTakenTv);
    }
}
