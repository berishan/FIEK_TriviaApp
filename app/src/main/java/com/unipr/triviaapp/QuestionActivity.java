package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.unipr.triviaapp.clients.QuestionClient;
import com.unipr.triviaapp.entities.Question;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import java.util.ArrayList;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity  {

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private int mCurrentPosition = 1;
    private ArrayList<Question> mQuestionsList = null;
    private int mSelectedOption = 0;
    private int mCorrectAnswers = 0;

    private int mScore = 0;
    private int mStreak = 0;
    private String mUserName = null;

    private ProgressBar progressBar;
    private TextView tvProgressText, tvQuestion;
    private TextView tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour, tvCountdown;


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
        tvCountdown = findViewById(R.id.tvCountdown);

        String seconds = "getIntent().getIntExtra(\"SECONDS\")";
        timeLeftInMillis = 60*1000;

        mUserName = "getIntent().getStringExtra(\"USERNAME\");";
        mQuestionsList = QuestionClient.getQuestions(0,0);

        progressBar.setMax(mQuestionsList.size());

        setQuestion();

        tvOptionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopCountdown();
                lockOptions(false);
                mSelectedOption = 1;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                        lockOptions(true);
                    }
                }, 2000);

            }
        });

        tvOptionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopCountdown();
                lockOptions(false);
                mSelectedOption = 2;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                        lockOptions(true);
                    }
                }, 2000);
            }
        });

        tvOptionThree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopCountdown();
                lockOptions(false);
                mSelectedOption = 3;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                        lockOptions(true);
                    }
                }, 2000);

            }
        });

        tvOptionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopCountdown();
                lockOptions(false);
                mSelectedOption = 4;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeQuestion();
                        lockOptions(true);
                    }
                }, 2000);

            }
        });

    }

    private void validateAnswer(){
            Question question = mQuestionsList.get(mCurrentPosition - 1);
            if(question.getCorrectAnswer() != mSelectedOption){
                answerView(mSelectedOption, R.drawable.wrong_option_bg);
                mStreak = 0;
            } else {
                mCorrectAnswers++;
                mStreak++;
                mScore = mScore + 100*mStreak;
            }
            answerView(question.getCorrectAnswer(), R.drawable.correct_option_bg);
            mCurrentPosition++;
            mSelectedOption = 0;
        }

     private void changeQuestion(){
        if (mCurrentPosition <= mQuestionsList.size()) {
            setQuestion();
        } else {
            mScore = mScore + mCorrectAnswers;
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            intent.putExtra("USERNAME", mUserName);
            intent.putExtra(ExtrasHelper.CORRECT_ANSWERS, mCorrectAnswers);
            intent.putExtra(ExtrasHelper.TOTAL_QUESTIONS, mQuestionsList.size());
            intent.putExtra(ExtrasHelper.SCORE, mScore);
            startActivity(intent);
            finish();
        }
     }


    private void setQuestion(){
        startCountdown();
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

    private void lockOptions(boolean isClickable){
        tvOptionOne.setClickable(isClickable);
        tvOptionTwo.setClickable(isClickable);
        tvOptionThree.setClickable(isClickable);
        tvOptionFour.setClickable(isClickable);
    }

    private void startCountdown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownView();
            }

            @Override
            public void onFinish() {
                validateAnswer();
                changeQuestion();
            }
        }.start();
    }

    private void updateCountdownView() {
        int minutes = (int) timeLeftInMillis/ 60000;
        int seconds = (int) timeLeftInMillis % 60000 / 1000;

        String timeLeftString;

        timeLeftString = "";
        if(minutes < 10) timeLeftString+= "0";
        timeLeftString +=  minutes;
        timeLeftString += ":";
        if(seconds < 10) timeLeftString += "0";
        timeLeftString += seconds;

        tvCountdown.setText(timeLeftString);
    }

    private void stopCountdown(){
        countDownTimer.cancel();
        mScore += timeLeftInMillis/10000;
        timeLeftInMillis = 60000;


    }
}