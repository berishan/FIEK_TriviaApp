package com.unipr.triviaapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unipr.triviaapp.adapters.ResultAdapter;
import com.unipr.triviaapp.db.DatabaseHelper;
import com.unipr.triviaapp.db.Queries;
import com.unipr.triviaapp.entities.Result;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    ListView resultsListView;
    ResultAdapter adapter;

    String email;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        resultsListView = (ListView) getView().findViewById(R.id.resultsListView);
        List<Result> resultList = new ArrayList<>();
        if (getArguments() != null) {
            email = this.getArguments().getString(ExtrasHelper.EMAIL);
        } else {
            email = "muti123";
        }
        adapter = new ResultAdapter(this.getContext());
        resultsListView.setAdapter(adapter);
        adapter.setResultList(getResults(email));
        adapter.notifyDataSetChanged();
    }

    private List<Result> getResults(String email){
        List<Result> results = new ArrayList<>();
        SQLiteDatabase database = new DatabaseHelper(this.getContext()).getReadableDatabase();
        Cursor cursor = database.rawQuery(Queries.GET_RESULTS, new String[] { email});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            results.add(new Result(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)+"",
                    cursor.getInt(3)+"",
                    cursor.getInt(4)+"",
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)
            ));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return results;
    }
}