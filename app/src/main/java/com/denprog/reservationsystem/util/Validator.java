package com.denprog.reservationsystem.util;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Pattern spaceMatcher = Pattern.compile("\\s");
    private static Pattern passwordStructure = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$");
    private static Pattern passwordContent = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_]).*$");

    public static boolean isInputNullAndEmpty(String input) {
        return input == null || input.isEmpty();
    }

    public static boolean isEmailValid(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }

    public static boolean isInputLengthValid(String input, int length) {
        return input.length() >= length;
    }

    public static boolean doesStringContainSpace(String input) {
        Matcher matcher = spaceMatcher.matcher(input);
        return matcher.matches();
    }

    public static boolean doesPasswordMatchProperStructure(String password) {
        Matcher matcher = passwordStructure.matcher(password);
        return matcher.matches();
    }

    public static String validateName(String name) {
        if (isInputNullAndEmpty(name)) {
            return "Empty Field";
        }
        return null;
    }

    public static String validateEmail(String email) {
        if (isInputNullAndEmpty(email)) {
            return "Empty Field";
        } else if (!isEmailValid(email)) {
            return "Invalid Email";
        }
        return null;
    }

    public static boolean validatePasswordLengthAndContent(String password) {
        Matcher matcher = passwordStructure.matcher(password);
        return matcher.matches();
    }

    public static String passwordValidation(String password) {
        if (isInputNullAndEmpty(password)) {
            return "Input Null";
        } else if (!validatePasswordLengthAndContent(password)) {
            return "Password Invalid";
        }
        return null;
    }

    public static boolean isPasswordContentValid(String password) {
        Matcher matcher = passwordContent.matcher(password);
        return matcher.matches();
    }

    public static boolean isPasswordLengthValid(String password) {
        return password.length() >= 8;
    }

}
