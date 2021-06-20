package com.unipr.triviaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.unipr.triviaapp.R;
import com.unipr.triviaapp.entities.Result;
import com.unipr.triviaapp.view_holders.ResultsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends BaseAdapter {

    Context context;
    List<Result> resultList = new ArrayList<>();


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public ResultAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return resultList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ResultsViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.result_adapter_view_layout, parent, false);
            viewHolder = new ResultsViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ResultsViewHolder) convertView.getTag();
        }
        viewHolder.getCategory().setText("Category: " + resultList.get(position).getCategory());
        viewHolder.getDifficulty().setText("Difficulty: " + resultList.get(position).getDifficulty());
        viewHolder.getScore().setText("Score: " + resultList.get(position).getTotalPoints());
        viewHolder.getQuestions().setText("Questions: " + resultList.get(position).getCorrectAnswers() + "/" + resultList.get(position).getNumberOfQuestions());
        viewHolder.getDateTaken().setText("Date: " + resultList.get(position).getDate());


//        viewHolder.getUsersEmail().setText("User:" + resultList.get(position).getUser());
//        viewHolder.getCorrectAnswers().setText("Correct answers:" + resultList.get(position).getCorrectAnswers());
//        viewHolder.getNumberOfPoints().setText("Number of points:" + resultList.get(position).getTotalPoints());
////        viewHolder.getNumberOfQuestions().setText("Number of questions:" + resultList.get(position).getNumberOfQuestions());
//        viewHolder.getDateTaken().setText("Date taken:" + resultList.get(position).getDate());


//        viewHolder.getCorrectAnswers().setText(resultList.get(position)
//        .getCorrectAnswers());
//        viewHolder.getDateTaken().setText(
//                resultList.get(position).getDate()
//        );
//        viewHolder.getNumberOfPoints().setText(
//                resultList.get(position).getTotalPoints());
//        viewHolder.getNumberOfQuestions().setText(
//                resultList.get(position).getNumberOfQuestions());

        return convertView;
    }
}
