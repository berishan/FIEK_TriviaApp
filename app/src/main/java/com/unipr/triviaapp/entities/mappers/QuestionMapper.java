package com.unipr.triviaapp.entities.mappers;

import com.unipr.triviaapp.entities.ApiResult;
import com.unipr.triviaapp.entities.Question;

public class QuestionMapper {

    public static Question mapApiResultToQuestion(ApiResult apiResult) {
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
}
