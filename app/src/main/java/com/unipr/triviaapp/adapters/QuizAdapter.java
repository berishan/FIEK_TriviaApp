package com.unipr.triviaapp.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.unipr.triviaapp.R;
import com.unipr.triviaapp.db.DBConfig;
import com.unipr.triviaapp.db.DatabaseHelper;
import com.unipr.triviaapp.entities.Quiz;
import com.unipr.triviaapp.view_holders.QuizViewHolder;
import com.unipr.triviaapp.view_holders.ResultsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends BaseAdapter {
    Context context;
    List<Quiz> quizList = new ArrayList();
    Button btnStartQuiz, btnDeleteQuiz;


    public QuizAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Quiz> getQuizList() {
        return this.quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }



    @Override
    public int getCount() {
        return this.quizList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.quizList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.quizList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuizViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.create_quiz_adapter_view_layout, parent, false);
            viewHolder = new QuizViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (QuizViewHolder) convertView.getTag();
        }

        viewHolder.getTvQuizName().setText("Name: " + quizList.get(position).getName());
        viewHolder.getTvDateCreated().setText("Created at: " + quizList.get(position).getDateCreated());
        viewHolder.getTvNumberOfQuestions().setText("Number of questions:" + quizList.get(position).getNumberOfQuestions());

        btnStartQuiz = convertView.findViewById(R.id.btnStartQuiz);
        btnDeleteQuiz = convertView.findViewById(R.id.btnDeleteQuiz);

        btnDeleteQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuiz(position);
            }
        });

        return convertView;

    }


    private void deleteQuiz(int position){
        Quiz quiz = quizList.get(position);
        SQLiteDatabase database = new DatabaseHelper(getContext()).getWritableDatabase();
        database.delete(DBConfig.QUIZZES_TABLE_NAME, " id = " + "'" + quiz.getId() + "';", null);
        database.close();
    }
}
