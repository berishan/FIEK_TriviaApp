package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.unipr.triviaapp.clients.QuestionClient;
import com.unipr.triviaapp.entities.Question;

import java.util.ArrayList;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity  {

    private int mCurrentPosition = 1;
    private ArrayList<Question> mQuestionsList = null;
    private int mSelectedOption = 0;
    private int mCorrectAnswers = 0;
    private String mUserName = null;

    private ProgressBar progressBar;
    private TextView tvProgressText, tvQuestion;
    private TextView tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        progressBar = findViewById(R.id.progressBar);
        tvProgressText = findViewById(R.id.tvProgress);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvOptionOne = findViewById(R.id.tvOptionOne);
        tvOptionTwo = findViewById(R.id.tvOptionTwo);
        tvOptionThree = findViewById(R.id.tvOptionThree);
        tvOptionFour = findViewById(R.id.tvOptionFour);


        mUserName = "getIntent().getStringExtra(\"USERNAME\");";
        mQuestionsList = QuestionClient.getQuestions(0,0);

        setQuestion();

        tvOptionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedOption = 1;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                    }
                }, 1000);

            }
        });

        tvOptionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedOption = 2;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                    }
                }, 1000);
            }
        });

        tvOptionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedOption = 3;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                    }
                }, 1000);

            }
        });

        tvOptionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedOption = 4;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                    }
                }, 1000);

            }
        });

    }

    private void validateAnswer(){
            Question question = mQuestionsList.get(mCurrentPosition - 1);
            if(question.getCorrectAnswer() != mSelectedOption){
                answerView(mSelectedOption, R.drawable.wrong_option_bg);
            } else {
                mCorrectAnswers++;
            }
            answerView(question.getCorrectAnswer(), R.drawable.correct_option_bg);
            mCurrentPosition++;
        }

     private void changeQuestion(){
        if (mCurrentPosition <= mQuestionsList.size()) {
            setQuestion();
        } else {
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            intent.putExtra("USERNAME", mUserName);
            intent.putExtra("CORRECTANSWERS", mCorrectAnswers);
            intent.putExtra("TOTALQUESTIONS", mQuestionsList.size());
            startActivity(intent);
            finish();
        }
     }


    private void setQuestion(){
        Question question =  mQuestionsList.get(mCurrentPosition- 1);
        defaultOptionsView();

        progressBar.setProgress(mCurrentPosition);
        tvProgressText.setText(String.format(Locale.ITALIAN,"%d/%d", mCurrentPosition, progressBar.getMax()));

        tvQuestion.setText(question.getQuestion());
        tvOptionOne.setText(question.getOptionOne());
        tvOptionTwo.setText(question.getOptionTwo());
        tvOptionThree.setText(question.getOptionThree());
        tvOptionFour.setText(question.getOptionFour());
    }


    private void defaultOptionsView(){
        ArrayList<TextView> options = new ArrayList<>();
        options.add(0, tvOptionOne);
        options.add(1, tvOptionTwo);
        options.add(2, tvOptionThree);
        options.add(3, tvOptionFour);

        for (TextView option: options) {
            option.setTextColor(Color.parseColor("#FF424242"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable(
                    QuestionActivity.this,
                    R.drawable.default_option_bg));

        }

    }

    private void answerView(int answer, int drawableView){
        switch (answer){
            case 1: tvOptionOne.setBackground(ContextCompat.getDrawable(
                    QuestionActivity.this, drawableView
                    ));
                    break;
            case 2: tvOptionTwo.setBackground(ContextCompat.getDrawable(
                    QuestionActivity.this, drawableView
                    ));
                    break;
            case 3: tvOptionThree.setBackground(ContextCompat.getDrawable(
                    QuestionActivity.this, drawableView
                    ));
                    break;
            case 4: tvOptionFour.setBackground(ContextCompat.getDrawable(
                    QuestionActivity.this, drawableView
                    ));
                    break;

        }
    }
}