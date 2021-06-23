package com.unipr.triviaapp.view_holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipr.triviaapp.R;

import org.w3c.dom.Text;

public class LeaderboardViewHolder {

    TextView leaderboardUsernameTv, leaderboardEmailTv, leaderboardScoreTv, leaderBoardCategoryTv, leaderboardDifficultyTv;
    LinearLayout linearLayout;

    public TextView getLeaderboardUsernameTv() {
        return leaderboardUsernameTv;
    }

    public void setLeaderboardUsernameTv(TextView leaderboardUsernameTv) {
        this.leaderboardUsernameTv = leaderboardUsernameTv;
    }

    public TextView getLeaderboardEmailTv() {
        return leaderboardEmailTv;
    }

    public void setLeaderboardEmailTv(TextView leaderboardEmailTv) {
        this.leaderboardEmailTv = leaderboardEmailTv;
    }

    public TextView getLeaderboardScoreTv() {
        return leaderboardScoreTv;
    }

    public void setLeaderboardScoreTv(TextView leaderboardScoreTv) {
        this.leaderboardScoreTv = leaderboardScoreTv;
    }

    public TextView getLeaderBoardCategoryTv() {
        return leaderBoardCategoryTv;
    }

    public void setLeaderBoardCategoryTv(TextView leaderBoardCategoryTv) {
        this.leaderBoardCategoryTv = leaderBoardCategoryTv;
    }

    public TextView getLeaderboardDifficultyTv() {
        return leaderboardDifficultyTv;
    }

    public void setLeaderboardDifficultyTv(TextView leaderboardDifficultyTv) {
        this.leaderboardDifficultyTv = leaderboardDifficultyTv;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public LeaderboardViewHolder(View view) {
        leaderboardUsernameTv = view.findViewById(R.id.leaderboardUserTv);
        leaderboardEmailTv = view.findViewById(R.id.leaderboardEmailTv);
        leaderBoardCategoryTv = view.findViewById(R.id.leaderboardCategoryTv);
        leaderboardScoreTv = view.findViewById(R.id.leaderboardScoreTv);
        leaderboardDifficultyTv =view.findViewById(R.id.leaderboardDifficultyTv);
        linearLayout = view.findViewById(R.id.leaderboardLinearLayout);
    }
}
