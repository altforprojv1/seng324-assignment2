package com.seng324;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class AccountValidator {
    public static final int NAME_MIN_LENGTH = 1;
    public static final int NAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 64;

    public static boolean isValidFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty())
            return false;
        if (firstName.length() < NAME_MIN_LENGTH || firstName.length() > NAME_MAX_LENGTH)
            return false;
        return firstName.matches("[a-zA-Z]+");
    }

    public static boolean isValidLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty())
            return false;
        if (lastName.length() < NAME_MIN_LENGTH || lastName.length() > NAME_MAX_LENGTH)
            return false;
        return lastName.matches("[a-zA-Z]+");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty())
            return false;
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isValidDateOfBirth(String dob) {
        if (dob == null)
            return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate birthDate = LocalDate.parse(dob, formatter);
            LocalDate now = LocalDate.now();
            if (birthDate.isAfter(now))
                return false;
            if (birthDate.isBefore(now.minusYears(120)))
                return false;
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidPassword(String password) {
        if (password == null
                || password.length() < PASSWORD_MIN_LENGTH
                || password.length() > PASSWORD_MAX_LENGTH)
            return false;
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        return hasUpper && hasDigit && hasSpecial;
    }

    public static boolean isPasswordMatch(String password, String confirmPassword) {
        if (password == null || confirmPassword == null)
            return false;
        return password.equals(confirmPassword);
    }
}