package com.unipr.triviaapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.unipr.triviaapp.entities.ApiResult;
import com.unipr.triviaapp.entities.Question;
import com.unipr.triviaapp.entities.QuestionApiEntity;
import com.unipr.triviaapp.entities.mappers.QuestionMapper;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import org.apache.commons.lang3.StringEscapeUtils;
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

public class QuestionActivity extends AppCompatActivity {

    final Map<String, Integer> categoryMap = new HashMap<>();
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private int mCurrentPosition = 1;
    private final ArrayList<Question> mQuestionsList = new ArrayList<>();
    private int numberOfQuestions;
    private String difficulty;
    private String category;
    private int mSelectedOption = 0;
    private int mCorrectAnswers = 0;
    private int mScore = 0;
    private int mStreak = 1;
    private String mUserName;
    private ProgressBar progressBar;
    private TextView tvProgressText, tvQuestion;
    private TextView tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour, tvCountdown;
    private TextView tvMainCountdown;
    private MediaPlayer mediaPlayer;

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to exit?")
                .setMessage("You will lose the progress of the quiz!")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(QuestionActivity.this, CoreActivity.class);
                    intent.putExtra(ExtrasHelper.FULL_NAME, mUserName);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        categoryMap.put("General Knowledge", 9);
        categoryMap.put("Sports", 21);
        categoryMap.put("History", 23);
        categoryMap.put("Geography", 22);
        categoryMap.put("Science: Computers", 18);
        categoryMap.put("Entertainment: Comics", 29);

        timeLeftInMillis = 30 * 1000;

        difficulty = getIntent().getStringExtra(ExtrasHelper.DIFFICULTY);
        category = getIntent().getStringExtra(ExtrasHelper.CATEGORY);
        numberOfQuestions = getIntent().getIntExtra(ExtrasHelper.TOTAL_QUESTIONS, 0);
        mUserName = getIntent().getStringExtra(ExtrasHelper.FULL_NAME);
        tvMainCountdown = findViewById(R.id.tvMainCountdown);

        new QuestionActivityAsync().execute();


    }

    private void setUI() {
        setContentView(R.layout.activity_question);
        progressBar = findViewById(R.id.progressBar);
        tvProgressText = findViewById(R.id.tvProgress);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvOptionOne = findViewById(R.id.tvOptionOne);
        tvOptionTwo = findViewById(R.id.tvOptionTwo);
        tvOptionThree = findViewById(R.id.tvOptionThree);
        tvOptionFour = findViewById(R.id.tvOptionFour);
        tvCountdown = findViewById(R.id.tvCountdown);


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

    private void validateAnswer() {
        Question question = mQuestionsList.get(mCurrentPosition - 1);
        if (question.getCorrectAnswer() != mSelectedOption) {
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

    private void changeQuestion() {
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


    private void setQuestion() {
        startCountdown();
        Question question = mQuestionsList.get(mCurrentPosition - 1);
        defaultOptionsView();

        progressBar.setProgress(mCurrentPosition);
        tvProgressText.setText(String.format(Locale.ITALIAN, "%d/%d", mCurrentPosition, progressBar.getMax()));

        tvQuestion.setText(question.getQuestion());
        tvOptionOne.setText(question.getOptionOne());
        tvOptionTwo.setText(question.getOptionTwo());
        tvOptionThree.setText(question.getOptionThree());
        tvOptionFour.setText(question.getOptionFour());
    }


    private void defaultOptionsView() {
        ArrayList<TextView> options = new ArrayList<>();
        options.add(0, tvOptionOne);
        options.add(1, tvOptionTwo);
        options.add(2, tvOptionThree);
        options.add(3, tvOptionFour);

        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#FF424242"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable(
                    QuestionActivity.this,
                    R.drawable.default_option_bg));

        }

    }

    private void answerView(int answer, int drawableView) {
        switch (answer) {
            case 1:
                tvOptionOne.setBackground(ContextCompat.getDrawable(
                        QuestionActivity.this, drawableView
                ));
                break;
            case 2:
                tvOptionTwo.setBackground(ContextCompat.getDrawable(
                        QuestionActivity.this, drawableView
                ));
                break;
            case 3:
                tvOptionThree.setBackground(ContextCompat.getDrawable(
                        QuestionActivity.this, drawableView
                ));
                break;
            case 4:
                tvOptionFour.setBackground(ContextCompat.getDrawable(
                        QuestionActivity.this, drawableView
                ));
                break;


        }
    }

    private void lockOptions(boolean isClickable) {
        tvOptionOne.setClickable(isClickable);
        tvOptionTwo.setClickable(isClickable);
        tvOptionThree.setClickable(isClickable);
        tvOptionFour.setClickable(isClickable);
    }

    private void startCountdown() {
        mediaPlayer =  MediaPlayer.create(QuestionActivity.this, R.raw.countdown);
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownView();
                if (timeLeftInMillis < 10000) {
                    mediaPlayer.start();
                    tvCountdown.setTextColor(getResources().getColor(R.color.red));
                }
            }

            @Override
            public void onFinish() {
                mediaPlayer.stop();
                mSelectedOption = 0;
                validateAnswer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timeLeftInMillis = 30000;
                        tvCountdown.setTextColor(getResources().getColor(R.color.black));
                        changeQuestion();
                    }
                }, 2000);

            }
        }.start();
    }

    private void updateCountdownView() {
        int minutes = (int) timeLeftInMillis / 60000;
        int seconds = (int) timeLeftInMillis % 60000 / 1000;

        String timeLeftString;

        timeLeftString = "";
        if (minutes < 10) timeLeftString += "0";
        timeLeftString += minutes;
        timeLeftString += ":";
        if (seconds < 10) timeLeftString += "0";
        timeLeftString += seconds;

        tvCountdown.setText(timeLeftString);
    }

    private void stopCountdown() {
        mediaPlayer.stop();
        tvCountdown.setTextColor(getResources().getColor(R.color.black));
        countDownTimer.cancel();
        mScore += mStreak * timeLeftInMillis / 10000;
        timeLeftInMillis = 30000;
    }


/////////////////////////////////////////////////////////////////////////////////////////////////

    class QuestionActivityAsync extends AsyncTask<Void, Void, Void> {

        final String URL = "https://opentdb.com/api.php?amount=%d&category=%d&difficulty=%s&type=multiple";
        OkHttpClient client = new OkHttpClient();
        String apiURL = String.format(URL, numberOfQuestions, categoryMap.get(category), difficulty.toLowerCase());

        List<ApiResult> list = new ArrayList<>();

        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url(apiURL)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String res = response.body().string();
                        Gson gsonParser = new Gson();
                        QuestionApiEntity questionApiEntity = gsonParser.fromJson(res, QuestionApiEntity.class);
                        list = questionApiEntity.getResults();
                        for (ApiResult member : list) {
                            member.question = StringEscapeUtils.unescapeHtml4(member.question);
                            member.correct_answer = StringEscapeUtils.unescapeHtml4(member.correct_answer);
                            List<String> inc = new ArrayList<>();
                            for(int i = 0; i < 3; i++){
                                String string = member.incorrect_answers.get(i);
                                string = StringEscapeUtils.unescapeHtml4(string);
                                inc.add(string);
                            }
                            member.incorrect_answers = inc;
                            mQuestionsList.add(QuestionMapper.mapApiResultToQuestion(member));
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setUI();
                                progressBar.setMax(mQuestionsList.size());
                                setQuestion();
                            }
                        });


                    } else {
                        call.cancel();

                    }
                }
            });
            return null;
        }
    }


}