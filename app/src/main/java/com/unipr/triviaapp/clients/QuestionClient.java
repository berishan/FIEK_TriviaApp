package com.unipr.triviaapp.clients;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.unipr.triviaapp.QuestionActivity;
import com.unipr.triviaapp.entities.ApiResult;
import com.unipr.triviaapp.entities.Question;
import com.unipr.triviaapp.entities.QuestionApiEntity;
import com.unipr.triviaapp.entities.mappers.QuestionMapper;

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



    public static void getQuestions(int amount, String category, String difficulty, ArrayList<Question> questionsArrayList){

        categoryMap.put("General Knowledge", 9);
        categoryMap.put("Sports", 21);
        categoryMap.put("History", 23);
        categoryMap.put("Geography", 22);
        categoryMap.put("Science: Computers", 18);
        categoryMap.put("Art", 25);

        String apiURL = String.format(baseURL, 10, categoryMap.get("Sports"
        ), "medium");

        getData(apiURL,  questionsArrayList);
        Log.println(Log.INFO, "Test", apiURL);

    }

    public static void getData(String url, ArrayList<Question> questionArrayList) {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Log.println(Log.INFO, "Test 76", request.toString());

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                call.cancel();
                list = new ArrayList<>();
                list.add(new ApiResult());
                Log.println(Log.INFO, "Test 84", request.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.println(Log.INFO, "Test 89", request.toString());
                if (response.isSuccessful()) {
                    String res = response.body().string();
                    Log.println(Log.INFO, "Test 92", request.toString());
                    Gson gsonParser = new Gson();
                    QuestionApiEntity questionApiEntity = gsonParser.fromJson(res, QuestionApiEntity.class);
                    list = questionApiEntity.getResults();
                    for(ApiResult member: list){
                        questionArrayList.add(QuestionMapper.mapApiResultToQuestion(member));
                    }
                } else {
                    call.cancel();
                    Log.println(Log.INFO, "Test 99", request.toString());

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