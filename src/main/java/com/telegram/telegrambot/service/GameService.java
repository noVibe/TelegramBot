package com.telegram.telegrambot.service;

import com.telegram.telegrambot.model.Game;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GameService {
    public void initializeGame(Game game, int range) {
        game.setAttempts(calculateAttemptsAmount(range));
        game.setGuessedNumber(getRandomNumber(range));
        game.setMessage("Lets' go! You got " + game.getAttempts() + " attempts. Make a guess");
    }

    public void play(Game game, int number) {
        String message;
        game.decreaseAttempts();
        int guessedNumber = game.getGuessedNumber();
        if (number == guessedNumber) {
            game.finish();
            message = "You guessed! It's " + guessedNumber + "!";
        } else if (game.isOver()) {
            message = "No attempts left. You lost! Guessed number was " + guessedNumber;
        } else if (number < guessedNumber) {
            message = "Guessed number is bigger!\n" + game.getAttempts() + " attempts left.";
        } else
            message = "Guessed number is smaller!\n" + game.getAttempts() + " attempts left.";
        game.setMessage(message);
    }


    private int calculateAttemptsAmount(int range) {
        return range <= 2 ? range : (int) Math.ceil(Math.log(range) / Math.log(2));
    }

    private int getRandomNumber(int range) {
        return new Random().nextInt(1, range == Integer.MAX_VALUE ? range : range + 1);
    }
}
