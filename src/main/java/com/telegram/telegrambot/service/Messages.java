package com.telegram.telegrambot.service;

public enum Messages {

    START_GAME("Choose the range of guessing. Use integer value"),
    STOP_GAME("Game has been stopped"),
    GAME_NOT_FOUND("Make sure you've started the game via /start"),
    NOTHING_TO_REMOVE("You don't have an active game"),
    INVALID_INPUT("Invalid input! Try /help if you stuck"),
    HELP("""
            Use commands:
            /start - start a new game or restart
            /stop - stop active game
            During a game just send an integer value.
            If you can't win, read about binary search algorithm :)
            """);
    public final String message;

    Messages(String message) {
        this.message = message;
    }
}
