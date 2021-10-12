package com.unipr.triviaapp.view_holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipr.triviaapp.R;

public class LeaderboardViewHolder {

    TextView leaderboardUsernameTv, leaderboardScoreTv;
    LinearLayout linearLayout;

    public LeaderboardViewHolder(View view) {
        leaderboardUsernameTv = view.findViewById(R.id.userTv);
        leaderboardScoreTv = view.findViewById(R.id.scoreTv);

    }

    public TextView getLeaderboardUsernameTv() {
        return leaderboardUsernameTv;
    }

    public void setLeaderboardUsernameTv(TextView leaderboardUsernameTv) {
        this.leaderboardUsernameTv = leaderboardUsernameTv;
    }

    public TextView getLeaderboardScoreTv() {
        return leaderboardScoreTv;
    }

    public void setLeaderboardScoreTv(TextView leaderboardScoreTv) {
        this.leaderboardScoreTv = leaderboardScoreTv;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }
}
