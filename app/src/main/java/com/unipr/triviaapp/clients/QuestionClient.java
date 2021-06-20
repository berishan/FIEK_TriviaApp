package com.unipr.triviaapp.clients;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.unipr.triviaapp.QuestionActivity;
import com.unipr.triviaapp.entities.ApiResult;
import com.unipr.triviaapp.entities.Question;
import com.unipr.triviaapp.entities.QuestionApiEntity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionClient {


    static OkHttpClient client = new OkHttpClient();

    static final String baseURL = "https://opentdb.com/api.php?amount=%d&category=%d&difficulty=%s&type=multiple";

    static final Map<String, Integer> categoryMap = new HashMap<>();

    static List<ApiResult> list = new ArrayList<>();



    public static Question apiResultToQuestionMapper(ApiResult apiResult) {


        //random.nextInt(max - min + 1) + min

        Question question = new Question();
        question.setQuestion(apiResult.getQuestion());
        int rand = (int)(1 + (Math.random() * 4));

        switch (rand) {
            case 1:
                question.setOptionOne(apiResult.getCorrect_answer());
                question.setCorrectAnswer(1);
                question.setOptionTwo(apiResult.getIncorrect_answers().get(0));
                question.setOptionThree(apiResult.getIncorrect_answers().get(1));
                question.setOptionFour(apiResult.getIncorrect_answers().get(2));
                break;
            case 2:
                question.setOptionTwo(apiResult.getCorrect_answer());
                question.setCorrectAnswer(2);
                question.setOptionOne(apiResult.getIncorrect_answers().get(0));
                question.setOptionThree(apiResult.getIncorrect_answers().get(1));
                question.setOptionFour(apiResult.getIncorrect_answers().get(2));
                break;
            case 3:
                question.setOptionThree(apiResult.getCorrect_answer());
                question.setCorrectAnswer(3);
                question.setOptionOne(apiResult.getIncorrect_answers().get(0));
                question.setOptionTwo(apiResult.getIncorrect_answers().get(1));
                question.setOptionFour(apiResult.getIncorrect_answers().get(2));
                break;
            case 4:
                question.setOptionFour(apiResult.getCorrect_answer());
                question.setCorrectAnswer(4);
                question.setOptionOne(apiResult.getIncorrect_answers().get(0));
                question.setOptionThree(apiResult.getIncorrect_answers().get(1));
                question.setOptionTwo(apiResult.getIncorrect_answers().get(2));
                break;
        }

        return question;
    }

    public static ArrayList<Question> getQuestions(int amount, String category, String difficulty){

        categoryMap.put("General Knowledge", 9);
        categoryMap.put("Sports", 21);
        categoryMap.put("History", 23);
        categoryMap.put("Geography", 22);
        categoryMap.put("Science: Computers", 18);
        categoryMap.put("Art", 25);

        String apiURL = String.format(baseURL, amount, categoryMap.get(category), difficulty);

        getData(apiURL);

        ArrayList<Question> arrayList = new ArrayList();

        for (ApiResult apiResult : list) {
            arrayList.add(apiResultToQuestionMapper(apiResult));
        }

        return arrayList;
    }

    public static void getData(String url) {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                list = new ArrayList<>();
                list.add(new ApiResult());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String res = response.body().string();
                    Gson gsonParser = new Gson();
                    QuestionApiEntity questionApiEntity = gsonParser.fromJson(res, QuestionApiEntity.class);
                    list = questionApiEntity.getResults();
                } else {
                    call.cancel();

                }
            }
        });
    }
}
// arrayList.add(new Question(
//                1,
//                "What's my name?",
//                "Deez",
//                "Jeff",
//                "Heisenberg",
//                "Chicka chicka Slim Shady",
//                3));
//        arrayList.add(new Question(
//                1,
//                "What's my last name?",
//                "Nuts",
//                "Jeff",
//                "Heisenberg",
//                "Eminem",
//                4));
//        arrayList.add(new Question(
//                1,
//                "What's my age?",
//                "1",
//                "2",
//                "3",
//                "Chicka chicka Slim Shady",
//                1));
//        arrayList.add(new Question(
//                1,
//                "What's my name?",
//                "Deez",
//                "Jeff",
//                "Heisenberg",
//                "Chicka chicka Slim Shady",
//                3));