package com.telegram.telegrambot.model;

import java.util.Random;

public class Game {
    private Integer attempts;
    private int guessedNumber;

    public int calculateAndSetAttempts(int range) {
        guessedNumber = new Random().nextInt(1, range == Integer.MAX_VALUE ? range : range + 1);
        attempts = range <= 2 ? range : (int) Math.ceil(Math.log(range) / Math.log(2));
        return attempts;
    }

    public boolean areAttemptsSet() {
        return attempts != null;
    }

    public String getResultOfGuess(int number) {
        String message;
        attempts--;
        if (number < guessedNumber) {
            message = "Guessed number is bigger!\n" + attemptsMessage();
        } else if (number > guessedNumber) {
            message = "Guessed number is smaller!\n" + attemptsMessage();
        } else {
            message = "You guessed! It's " + guessedNumber + "!";
            attempts = 0;
        }
        return message;
    }

    public boolean isOver() {
        return attempts == 0;
    }

    private String attemptsMessage() {
        if (!isOver()) {
            return attempts + " attempts left.";
        } else {
            return "No attempts left. You lost! Guessed number was " + guessedNumber;
        }
    }

}
