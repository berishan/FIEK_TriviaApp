package com.unipr.triviaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.unipr.triviaapp.R;
import com.unipr.triviaapp.entities.Leaderboard;
import com.unipr.triviaapp.view_holders.LeaderboardViewHolder;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends BaseAdapter {

    Context context;
    List<Leaderboard> leaderboards= new ArrayList();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Leaderboard> getLeaderboards() {
        return leaderboards;
    }

    public void setLeaderboards(List<Leaderboard> leaderboards) {
        this.leaderboards = leaderboards;
    }

    public LeaderboardAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return leaderboards.size();
    }

    @Override
    public Object getItem(int position) {
        return leaderboards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return leaderboards.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeaderboardViewHolder leaderboardViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.leaderboard_adapter_view_layout, parent, false);
            leaderboardViewHolder = new LeaderboardViewHolder(convertView);
            convertView.setTag(leaderboardViewHolder);
        } else {
            leaderboardViewHolder = (LeaderboardViewHolder)convertView.getTag();
        }

        leaderboardViewHolder.getLeaderboardUsernameTv().setText("User:" +
                leaderboards.get(position).getUser());
        leaderboardViewHolder.getLeaderBoardCategoryTv().setText("Category:" +
                leaderboards.get(position).getCategory());
        leaderboardViewHolder.getLeaderboardDifficultyTv().setText("Difficulty: " +
                leaderboards.get(position).getDifficulty());
        leaderboardViewHolder.getLeaderboardEmailTv().setText("Email: " +
                leaderboards.get(position).getEmail());
        leaderboardViewHolder.getLeaderboardScoreTv().setText("Score: " +
                leaderboards.get(position).getScore());
        return convertView;
    }
}
