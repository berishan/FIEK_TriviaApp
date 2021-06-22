package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.unipr.triviaapp.db.DBConfig;
import com.unipr.triviaapp.db.DatabaseHelper;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView tvUsername, tvResult, tvScore;
    private MediaPlayer mediaPlayer = null;

    private final int TIMEOUT = 5000;

    private String username, category, difficulty;
    private int totalQuestions, correctAnswers, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

         username = getIntent().getStringExtra(ExtrasHelper.FULL_NAME);
         category = getIntent().getStringExtra(ExtrasHelper.CATEGORY);
         difficulty = getIntent().getStringExtra(ExtrasHelper.DIFFICULTY);


        tvUsername = findViewById(R.id.tvName);
        tvUsername.setText(username);

         totalQuestions = getIntent().getIntExtra(ExtrasHelper.TOTAL_QUESTIONS,0);
         correctAnswers = getIntent().getIntExtra(ExtrasHelper.CORRECT_ANSWERS, 0);
         score = getIntent().getIntExtra(ExtrasHelper.SCORE, 0);

        mediaPlayer =  MediaPlayer.create(ResultActivity.this, R.raw.result);
        mediaPlayer.start();
        tvResult = findViewById(R.id.tvResult);
        tvResult.setText(String.format("Correct answers: %d/%d", correctAnswers, totalQuestions));

        tvScore = findViewById(R.id.tvScore);
        tvScore.setText(String.format("Score: %d", score));

        saveToDb();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                Intent intent = new Intent(ResultActivity.this, CoreActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIMEOUT);

    }

    private void saveToDb() {
        SQLiteDatabase database = new DatabaseHelper(ResultActivity.this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConfig.USER, username);
        values.put(DBConfig.NUMBER_OF_QUESTIONS, totalQuestions);
        values.put(DBConfig.CORRECT_ANSWERS, correctAnswers);
        values.put(DBConfig.POINTS, score);
        values.put(DBConfig.DIFFICULTY, difficulty);
        values.put(DBConfig.CATEGORY, category);
        values.put(DBConfig.DATE,  new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(Calendar.getInstance().getTime()));
        try {
            long id = database.insert(DBConfig.TABLE_NAME, null, values );
            if(id < 0){
                Toast.makeText(ResultActivity.this, "Couldn't save results!", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(ResultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            database.close();
        }
    }
}