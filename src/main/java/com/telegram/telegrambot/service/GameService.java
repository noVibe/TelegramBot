package com.telegram.telegrambot.service;

import com.telegram.telegrambot.model.Game;
import com.telegram.telegrambot.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class GameService {

    private static final String START = "/start";
    private static final String STOP = "/stop";
    private static final String HELP = "/help";
    GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public String makeAnswer(Update update) {
        String id = getId(update);
        String text = getText(update);
        return switch (text) {
            case START -> startGameAndGetMessage(id);
            case STOP -> stopGameAndGetMessage(id);
            case HELP -> sendHelp();
            default -> playGameAndGetProgressMessage(id, text);
        };
    }


    private String startGameAndGetMessage(String id) {
        repository.addGame(id, new Game());
        return "Choose the range of guessing. Use integer value";
    }


    private String playGameAndGetProgressMessage(String id, String number) {
        String progressMessage;
        Game game = repository.getGame(id);
        if (game == null) {
            return "Make sure you've started the game via /start";
        }
        int num = Integer.parseInt(number);
        if (!game.areAttemptsSet()) {
            progressMessage = "Lets' go! You got " + game.calculateAndSetAttempts(num) + " attempts. Make a guess";
        } else {
            progressMessage = game.getResultOfGuess(num);
            if (game.isOver()) {
                repository.getGame(id);
            }
        }
        return progressMessage;
    }


    private String stopGameAndGetMessage(String id) {
        if (repository.deleteGame(id)) {
            return "Game has been stopped";
        }
        return "You don't have an active game";

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

