package com.unipr.triviaapp.db;

public class Queries {

    public static final String CREATE_RESULTS_TABLE = "CREATE TABLE results ( \n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tuser TEXT NOT NULL,\n" +
            "\tnumber_of_questions INTEGER NOT NULL,\n" +
            "\tcorrect_answers INTEGER NOT NULL,\n" +
            "\tpoints INTEGER NOT NULL,\n" +
            "\tdate TEXT,\n" +
            "\tdifficulty TEXT NOT NULL,\n" +
            "\tcategory TEXT NOT NULL\n" +
            ");";

    public static final String CREATE_QUIZZES_TABLE = "CREATE TABLE quizzes ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user TEXT NOT NULL," +
            "name TEXT NOT NULL," +
            "date TEXT NOT NULL," +
            "questions INTEGER NOT NULL" +
            ");";

    public static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE questions ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "quiz_id INTEGER NOT NULL," +
            "question TEXT NOT NULL," +
            "option_one TEXT NOT NULL," +
            "option_two TEXT NOT NULL," +
            "option_three TEXT NOT NULL," +
            "option_four TEXT NOT NULL," +
            "correct_answer INTEGER NOT NULL" +
            ");";

    public static final String GET_RESULTS = "SELECT * FROM results WHERE user = ?";

    public static final String GET_QUIZZES = "SELECT * FROM quizzes WHERE user = ?";

}