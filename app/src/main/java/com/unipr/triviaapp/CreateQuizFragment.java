package com.unipr.triviaapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.unipr.triviaapp.adapters.QuizAdapter;
import com.unipr.triviaapp.entities.Question;
import com.unipr.triviaapp.entities.Quiz;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateQuizFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView quizzesListView;
    Button btnCreateQuiz;
    QuizAdapter adapter;
    String email;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateQuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateQuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateQuizFragment newInstance(String param1, String param2) {
        CreateQuizFragment fragment = new CreateQuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        quizzesListView = getView().findViewById(R.id.quizesListView);
        btnCreateQuiz = getView().findViewById(R.id.btnCreateQuiz);
        adapter = new QuizAdapter(this.getContext());
        if(getArguments() != null){
            email = this.getArguments().getString(ExtrasHelper.EMAIL);
        }

        quizzesListView.setAdapter(adapter);
        adapter.setQuizList(readQuizzes(email));
        adapter.notifyDataSetChanged();

        btnCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Toast!", Toast.LENGTH_SHORT).show();
            }
        });





        super.onViewCreated(view, savedInstanceState);
    }

    private List<Quiz> readQuizzes(String email) {
        Question question = new Question();

        question.setQuestion("How are you?");
        question.setOptionOne("Fine.");
        question.setOptionTwo("Good.");
        question.setOptionThree("Great.");
        question.setOptionFour("Awesome.");
        question.setCorrectAnswer(1);

        List<Question> questions = new ArrayList<>();
        questions.add(question);

        List<Quiz> quizzes = new ArrayList<>();

        Quiz quiz = new Quiz();
        quiz.setId(342);
        quiz.setName("Quiz 1");
        quiz.setDateCreated("Dje h3h3");

        quiz.setQuestions(questions);
        quizzes.add(quiz);

        return quizzes;
    }
}