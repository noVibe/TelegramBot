package com.telegram.telegrambot.model;

public class Game {
    private Integer attempts;
    private int guessedNumber;
    private String message;


    public boolean isInitialized() {
        return attempts != null;
    }

    public boolean isOver() {
        return attempts == 0;
    }

    public void decreaseAttempts() {
        attempts--;
    }

    public void finish() {
        attempts = 0;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public int getGuessedNumber() {
        return guessedNumber;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public void setGuessedNumber(int guessedNumber) {
        this.guessedNumber = guessedNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
