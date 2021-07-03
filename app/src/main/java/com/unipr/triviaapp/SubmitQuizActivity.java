package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unipr.triviaapp.R;
import com.unipr.triviaapp.db.DBConfig;
import com.unipr.triviaapp.db.DatabaseHelper;
import com.unipr.triviaapp.entities.Question;
import com.unipr.triviaapp.entities.Quiz;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SubmitQuizActivity extends AppCompatActivity {

    EditText etQuestion, etCorrectAnswer;
    EditText etIncorrectAnswer1, etIncorrectAnswer2, etIncorrectAnswer3;

    Button btnNext, btnFinish;

    List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_quiz);

        etQuestion = findViewById(R.id.etQuestion);
        etCorrectAnswer = findViewById(R.id.etCorrectAnswer);

        etIncorrectAnswer1 = findViewById(R.id.etIncorrectAnswer1);
        etIncorrectAnswer2 = findViewById(R.id.etIncorrectAnswer2);
        etIncorrectAnswer3 = findViewById(R.id.etIncorrectAnswer3);

        btnNext = findViewById(R.id.buttonNext);
        btnFinish = findViewById(R.id.buttonFinish);

        questionList = new ArrayList<>();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requiredFieldsAreFilled()){
                    saveQuestion();
                    nextQuestion();
                }
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requiredFieldsAreFilled()){
                    saveQuestion();
                    saveQuiz();
                }
            }
        });

    }


    private boolean requiredFieldsAreFilled(){
        if (etQuestion.getText().toString().equals("")) {
            etQuestion.setError("Required field!");
            etQuestion.requestFocus();
            return false;
        }
        if(etCorrectAnswer.getText().toString().equals("")){
            etCorrectAnswer.setError("Required field!");
            etCorrectAnswer.requestFocus();
            return false;
        }
        if (etIncorrectAnswer1.getText().toString().equals("")) {
            etIncorrectAnswer1.setError("Required field!");
            etIncorrectAnswer1.requestFocus();
            return false;
        }
        if (etIncorrectAnswer2.getText().toString().equals("")) {
            etIncorrectAnswer2.setError("Required field!");
            etIncorrectAnswer2.requestFocus();
            return false;
        }
        if (etIncorrectAnswer3.getText().toString().equals("")) {
            etIncorrectAnswer3.setError("Required field!");
            etIncorrectAnswer3.requestFocus();
            return false;
        }
        return true;
    }


    private void saveQuestion(){
        Question question = new Question();
        question.setId(questionList.size());

        question.setQuestion(etQuestion.getText().toString());

        question.setOptionOne(etCorrectAnswer.getText().toString());
        question.setOptionTwo(etIncorrectAnswer1.getText().toString());
        question.setOptionThree(etIncorrectAnswer2.getText().toString());
        question.setOptionFour(etIncorrectAnswer3.getText().toString());

        question.setCorrectAnswer(1);

        questionList.add(question);
    }

    private void nextQuestion(){
        etQuestion.setText("");
        etCorrectAnswer.setText("");
        etIncorrectAnswer1.setText("");
        etIncorrectAnswer2.setText("");
        etIncorrectAnswer3.setText("");

    }

    private void saveQuiz(){
        Quiz quiz = new Quiz();

        quiz.setName("Default");
        quiz.setQuestions(questionList);
        quiz.setDateCreated(
                new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ITALY)
                        .format(Calendar.getInstance()
                                        .getTime()));

        quiz.setNumberOfQuestions(quiz.getQuestions().size());
        SQLiteDatabase database = new DatabaseHelper(SubmitQuizActivity.this).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBConfig.USER, "berishanora24.nb@gmail.com");
        values.put(DBConfig.QUIZ_NAME, quiz.getName());
        values.put(DBConfig.QUIZ_DATE, quiz.getDateCreated());
        values.put(DBConfig.QUIZ_QUESTIONS, quiz.getNumberOfQuestions());

        try {
            long id = database.insert(DBConfig.QUIZZES_TABLE_NAME,
                    null,
                    values);
            if(id < 0){
                Toast.makeText(SubmitQuizActivity.this, "Couldn't save results!", Toast.LENGTH_SHORT).show();
            } else {
                quiz.setId(((int) id));
                for (Question question: quiz.getQuestions()){
                    ContentValues val = new ContentValues();
                    val.put(DBConfig.QUESTIONS_QUIZ, quiz.getId());
                    val.put(DBConfig.QUESTION, question.getQuestion());
                    val.put(DBConfig.OPTION_ONE, question.getOptionOne());
                    val.put(DBConfig.OPTION_TWO, question.getOptionTwo());
                    val.put(DBConfig.OPTION_THREE, question.getOptionThree());
                    val.put(DBConfig.OPTION_FOUR, question.getOptionFour());
                    val.put(DBConfig.CORRECT_ANSWER, question.getCorrectAnswer());

                    long questionId = database.insert(DBConfig.QUESTIONS_TABLE_NAME,
                            null,
                            val);
                    if(questionId < 0){
                        Toast.makeText(SubmitQuizActivity.this, "Couldn't save results!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(SubmitQuizActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            database.close();
        }

    }


}