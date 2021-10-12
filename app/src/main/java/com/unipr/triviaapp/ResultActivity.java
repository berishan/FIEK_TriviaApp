package com.unipr.triviaapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipr.triviaapp.db.DBConfig;
import com.unipr.triviaapp.db.DatabaseHelper;
import com.unipr.triviaapp.entities.User;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private final int TIMEOUT = 5000;
    String email;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String userId;
    private TextView tvUsername, tvResult, tvScore;
    private MediaPlayer mediaPlayer = null;
    private int highScore;

    private String username, category, difficulty;
    private int multiplier = 1;
    private int totalQuestions, correctAnswers, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        username = getIntent().getStringExtra(ExtrasHelper.FULL_NAME);
        category = getIntent().getStringExtra(ExtrasHelper.CATEGORY);
        difficulty = getIntent().getStringExtra(ExtrasHelper.DIFFICULTY);

        if (difficulty != null) {
            switch (difficulty) {
                case "Medium":
                    multiplier = 2;
                    break;
                case "Hard":
                    multiplier = 3;
                    break;
                default:
                    multiplier = 1;
            }
        }


        totalQuestions = getIntent().getIntExtra(ExtrasHelper.TOTAL_QUESTIONS, 0);
        correctAnswers = getIntent().getIntExtra(ExtrasHelper.CORRECT_ANSWERS, 0);
        score = getIntent().getIntExtra(ExtrasHelper.SCORE, 0) * multiplier;

        tvUsername = findViewById(R.id.tvName);
        tvUsername.setText(username);
        tvResult = findViewById(R.id.tvResult);
        tvScore = findViewById(R.id.tvScore);

        tvResult.setText(String.format("Correct answers: %d/%d", correctAnswers, totalQuestions));
        tvScore.setText(String.format("Score: %d", score));

        mediaPlayer = MediaPlayer.create(ResultActivity.this, R.raw.result);
        mediaPlayer.start();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = firebaseUser.getUid();


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    email = user.getEmail();
                    highScore = user.getHighScore();
                    if (highScore < score) {
                        user.setHighScore(score);
                        reference.child(userId).setValue(user);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResultActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                Intent intent = new Intent(ResultActivity.this, CoreActivity.class);
                intent.putExtra(ExtrasHelper.EMAIL, email);
                startActivity(intent);
                saveToDb();
                finish();
            }
        }, TIMEOUT);

    }

    private void saveToDb() {
        if (difficulty != null) {
            SQLiteDatabase database = new DatabaseHelper(ResultActivity.this).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBConfig.USER, email);
            values.put(DBConfig.NUMBER_OF_QUESTIONS, totalQuestions);
            values.put(DBConfig.CORRECT_ANSWERS, correctAnswers);
            values.put(DBConfig.POINTS, score);
            values.put(DBConfig.DIFFICULTY, difficulty);
            values.put(DBConfig.CATEGORY, category);
            values.put(DBConfig.DATE, new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ITALY).format(Calendar.getInstance().getTime()));
            try {
                long id = database.insert(DBConfig.RESULT_TABLE_NAME, null, values);
                if (id < 0) {
                    Toast.makeText(ResultActivity.this, "Couldn't save results!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(ResultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                database.close();
            }
        }
    }
}