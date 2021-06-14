package com.unipr.triviaapp.helpers;

public class ValidateHelper {

    public static String checkIfEmpty(String text, String fieldName) {
        if (text.trim().isEmpty()) {
            return fieldName + " cannot be empty!";
        }
        return null;
    }

}
