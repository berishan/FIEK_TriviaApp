package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.unipr.triviaapp.helpers.ExtrasHelper;

public class ResultActivity extends AppCompatActivity {

    private TextView tvUsername, tvResult, tvScore;

    private final int TIMEOUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String username = getIntent().getStringExtra("USERNAME");
        tvUsername = findViewById(R.id.tvName);
        tvUsername.setText(username);

        int totalQuestions = getIntent().getIntExtra(ExtrasHelper.TOTAL_QUESTIONS,0);
        int correctAnswers = getIntent().getIntExtra(ExtrasHelper.CORRECT_ANSWERS, 0);
        int score = getIntent().getIntExtra(ExtrasHelper.SCORE, 0);

        tvResult = findViewById(R.id.tvResult);
        tvResult.setText(String.format("Correct answers: %d/%d", correctAnswers, totalQuestions));

        tvScore = findViewById(R.id.tvScore);
        tvScore.setText(String.format("Score: %d", score));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ResultActivity.this, CoreActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIMEOUT);

    }
}