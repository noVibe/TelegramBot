package com.telegram.telegrambot.service;

import com.telegram.telegrambot.model.Game;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {

    private static final String START = "/start";
    private static final String STOP = "/stop";
    private static final String HELP = "/help";
    Map<String, Game> activeGames = new HashMap<>();

    public String handleMessage(Update update) {
        String id = getId(update);
        String text = getText(update);

        return switch (text) {
            case START -> startGameAndGetMessage(id);
            case STOP -> stopGameAndGetMessage(id);
            case HELP -> sendHelp();
            default -> playGameAndGetProgressMessage(id, text);
        };
    }

    private boolean hasActiveGame(String id) {
        return activeGames.containsKey(id);
    }

    private String startGameAndGetMessage(String id) {
        Game game = new Game();
        activeGames.put(id, game);
        return "Choose the range of guessing. Use integer value";
    }

    private int validateInput(String input) {
        return Integer.parseInt(input);
    }

    private String playGameAndGetProgressMessage(String id, String number) {
        String progressMessage;
        if (!hasActiveGame(id)) {
            return "Make sure you've started the game via /start";
        }
        int num = validateInput(number);

        Game game = activeGames.get(id);
        if (!game.areAttemptsSet()) {
            progressMessage = "Lets' go! You got " + game.calculateAndSetAttempts(num) + " attempts. Make a guess";
        } else {
            progressMessage = game.getResultOfGuess(num);
            if (game.isOver()) {
                activeGames.remove(id);
            }
        }
        return progressMessage;
    }


    private String stopGameAndGetMessage(String id) {
        if (hasActiveGame(id)) {
            activeGames.remove(id);
            return "Game has been stopped";
        } else {
            return "You don't have an active game";
        }
    }

    private String sendHelp() {
        return """
                Use commands:
                /start - start a new game or restart
                /stop - stop active game
                During a game just send an integer value.
                If you can't win, read about binary search algorithm :)
                """;
    }


    public String getId(Update update) {
        return String.valueOf(update.getMessage().getChat().getId());
    }

    public String getText(Update update) {
        return update.getMessage().getText();
    }

}

