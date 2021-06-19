package com.unipr.triviaapp.db;

public class Queries {

    public static final String CREATE_RESULTS_TABLE = "create table results ( \n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tuser TEXT NOT NULL,\n" +
            "\tnumber_of_questions INTEGER NOT NULL,\n" +
            "\tcorrect_answers INTEGER NOT NULL,\n" +
            "\tpoints INTEGER NOT NULL\n" +
            ");";
}
