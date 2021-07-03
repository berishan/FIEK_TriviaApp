package com.unipr.triviaapp.view_holders;

import android.view.View;
import android.widget.TextView;

import com.unipr.triviaapp.R;

public class QuizViewHolder {

    TextView tvQuizName, tvDateCreated, tvNumberOfQuestions;

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

    public QuizViewHolder(View view){
        this.tvQuizName = view.findViewById(R.id.tvQuizName);
        this.tvDateCreated = view.findViewById(R.id.tvDateCreated);
        this.tvNumberOfQuestions = view.findViewById(R.id.tvQuestions);
    }
}
