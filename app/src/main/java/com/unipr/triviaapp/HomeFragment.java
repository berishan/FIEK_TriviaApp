package com.unipr.triviaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{

    Spinner categoriesSpinner;
    Spinner difficultySpinner;
    EditText numberOfQuestionsEt;
    Button startQuizButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String name;
    private String email;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static HomeFragment newInstance(String name) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
////        args.putString(ExtrasHelper.FULL_NAME,name);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        if (this.getArguments() == null) {
            name = "User";
        } else {
            name = this.getArguments().getString(ExtrasHelper.FULL_NAME);
            email = this.getArguments().getString(ExtrasHelper.EMAIL);
        }


        categoriesSpinner = (Spinner) getView().findViewById(R.id.category_spinner);
        difficultySpinner = (Spinner) getView().findViewById(R.id.difficulty_spinner);
        numberOfQuestionsEt = (EditText) getView().findViewById(R.id.etNumOfQuestions);
        startQuizButton = (Button) getView().findViewById(R.id.btnStartQuiz);


        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(getContext(), R.array.difficulty, android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);

        startQuizButton.setOnClickListener( e-> {
            startQuiz(name);
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void startQuiz(String name) {
        Integer numberOfQuestions;
        try {
            numberOfQuestions = Integer.parseInt(numberOfQuestionsEt.getEditableText().toString().trim());
            if (numberOfQuestions > 10 || numberOfQuestions < 5) {
                numberOfQuestionsEt.setError("The number of questions must be between 5 and 10");
                numberOfQuestionsEt.requestFocus();
                return;
            }
        } catch (Exception e) {
            numberOfQuestionsEt.setError("Please provide a number!");
            numberOfQuestionsEt.requestFocus();
            return;
        }

        String difficulty = difficultySpinner.getSelectedItem().toString();
        String category = categoriesSpinner.getSelectedItem().toString();
        numberOfQuestions = Integer.parseInt(numberOfQuestionsEt.getEditableText().toString());

        // https://opentdb.com/api.php?amount=10&category=23&difficulty=medium&type=multiple

        Intent intent = new Intent(this.getContext(), QuestionActivity.class);
        intent.putExtra(ExtrasHelper.FULL_NAME, name);
        intent.putExtra(ExtrasHelper.CATEGORY, category);
        intent.putExtra(ExtrasHelper.DIFFICULTY, difficulty);
        intent.putExtra(ExtrasHelper.TOTAL_QUESTIONS, numberOfQuestions);


        startActivity(intent);

    }

}