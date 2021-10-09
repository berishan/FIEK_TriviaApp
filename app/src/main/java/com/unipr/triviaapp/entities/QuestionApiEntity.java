package com.unipr.triviaapp.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionApiEntity{
    @SerializedName("response_code")
    public int response_code;
    @SerializedName("results")
    public List<ApiResult> results;

    public QuestionApiEntity(int response_code, List<ApiResult> results) {
        this.response_code = response_code;
        this.results = results;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<ApiResult> getResults() {
        return results;
    }

    public void setResults(List<ApiResult> results) {
        this.results = results;
    }
}