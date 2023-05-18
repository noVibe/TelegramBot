package com.telegram.telegrambot.model;

import java.util.Random;

public class Game {
    private Integer attempts;
    private int guessedNumber;
    private boolean isOver = false;

    public int calculateAndSetAttempts(int range) {
        guessedNumber = new Random().nextInt(range) + 1;
        attempts = (int) Math.ceil(Math.log(range) / Math.log(2));
        return attempts == 0 ? 1 : attempts;
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
            isOver = true;
        }
        return message;
    }

    private String attemptsMessage() {
        if (attempts != 0) {
            return attempts + " attempts left.";
        } else {
            isOver = true;
            return "No attempts left. You lost!";
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public Integer getAttempts() {
        return attempts;
    }
}
