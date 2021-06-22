package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.unipr.triviaapp.clients.QuestionClient;
import com.unipr.triviaapp.entities.ApiResult;
import com.unipr.triviaapp.entities.Question;
import com.unipr.triviaapp.entities.QuestionApiEntity;
import com.unipr.triviaapp.entities.mappers.QuestionMapper;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionActivity extends AppCompatActivity  {

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private int mCurrentPosition = 1;
    private ArrayList<Question> mQuestionsList = new ArrayList<>();

    private String difficulty;
    private String category;
    private int mSelectedOption = 0;
    private int mCorrectAnswers = 0;

    private int mScore = 0;
    private int mStreak = 1;
    private String mUserName = null;

    private ProgressBar progressBar;
    private TextView tvProgressText, tvQuestion;
    private TextView tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour, tvCountdown;
    private TextView tvMainCountdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

       // TODO String seconds = "getIntent().getIntExtra(\"SECONDS\")";
        timeLeftInMillis = 60*1000;

        difficulty = getIntent().getStringExtra(ExtrasHelper.DIFFICULTY);
        category = getIntent().getStringExtra(ExtrasHelper.CATEGORY);
        int numberOfQuestions = getIntent().getIntExtra(ExtrasHelper.TOTAL_QUESTIONS, 0);
        mUserName = getIntent().getStringExtra(ExtrasHelper.FULL_NAME);
        QuestionClient.getQuestions(numberOfQuestions, category, difficulty, mQuestionsList);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tvMainCountdown = findViewById(R.id.tvMainCountdown);



        setContentView(R.layout.activity_question);
        progressBar = findViewById(R.id.progressBar);
        tvProgressText = findViewById(R.id.tvProgress);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvOptionOne = findViewById(R.id.tvOptionOne);
        tvOptionTwo = findViewById(R.id.tvOptionTwo);
        tvOptionThree = findViewById(R.id.tvOptionThree);
        tvOptionFour = findViewById(R.id.tvOptionFour);
        tvCountdown = findViewById(R.id.tvCountdown);

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

    private void changeLayout() {
        for(int i = 9; i <= 0; i--){
            Log.println(Log.INFO, "Loop info Fiek", "Loop"+ i);
            tvMainCountdown.setText(i+"");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void validateAnswer(){
            Question question = mQuestionsList.get(mCurrentPosition - 1);
            if(question.getCorrectAnswer() != mSelectedOption){
                answerView(mSelectedOption, R.drawable.wrong_option_bg);
                mStreak = 1;
            } else {
                mCorrectAnswers++;
                mScore += 150;
                mStreak++;
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
            intent.putExtra(ExtrasHelper.FULL_NAME, mUserName);
            intent.putExtra(ExtrasHelper.CORRECT_ANSWERS, mCorrectAnswers);
            intent.putExtra(ExtrasHelper.TOTAL_QUESTIONS, mQuestionsList.size());
            intent.putExtra(ExtrasHelper.DIFFICULTY, difficulty);
            intent.putExtra(ExtrasHelper.CATEGORY, category);
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timeLeftInMillis = 60000;
                        startCountdown();
                        changeQuestion();
                    }
                }, 2000);

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
        mScore += mStreak*timeLeftInMillis/10000;
        timeLeftInMillis = 60000;
    }


/////////////////////////////////////////////////////////////////////////////////////////////////




}