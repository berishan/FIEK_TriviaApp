package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView tvUsername, tvScore;

    private final int TIMEOUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String username = getIntent().getStringExtra("USERNAME");
        tvUsername = findViewById(R.id.tvName);
        tvUsername.setText(username);

        int totalQuestions = getIntent().getIntExtra("TOTALQUESTIONS",0);
        int correctAnswers = getIntent().getIntExtra("CORRECTANSWERS", 0);

        tvScore = findViewById(R.id.tvScore);
        tvScore.setText(String.format("Your score is %d out of %d", correctAnswers, totalQuestions));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ResultActivity.this, CoreActivity.class);
                intent.putExtra("USESRNAME", username);
                startActivity(intent);
                finish();
            }
        }, TIMEOUT);

    }
}