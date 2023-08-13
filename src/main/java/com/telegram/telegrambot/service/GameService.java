package com.telegram.telegrambot.service;

import com.telegram.telegrambot.model.Game;
import com.telegram.telegrambot.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.telegram.telegrambot.service.Messages.*;

@Service
public class GameService {

    private static final String START_COMMAND = "/start";
    private static final String STOP_COMMAND = "/stop";
    private static final String HELP_COMMAND = "/help";
    GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public String makeAnswer(Update update) {
        String id = getId(update);
        String text = getText(update);
        return switch (text) {
            case START_COMMAND -> startGameAndGetMessage(id);
            case STOP_COMMAND -> stopGameAndGetMessage(id);
            case HELP_COMMAND -> sendHelp();
            default -> playGameAndGetProgressMessage(id, text);
        };
    }


    private String startGameAndGetMessage(String id) {
        repository.addGame(id, new Game());
        return START_GAME.message;
    }


    private String playGameAndGetProgressMessage(String id, String number) {
        String progressMessage;
        Game game = repository.getGame(id);
        if (game == null) {
            return ACTIVE_GAME_NOT_FOUND.message;
        }
        int num = Integer.parseInt(number);
        if (game.isInitialized()) {
            progressMessage = game.getResultOfGuess(num);
            if (game.isOver()) {
                repository.deleteGame(id);
            }
        } else {
            game.initialize(num);
            progressMessage = "Lets' go! You got " + game.getAttempts() + " attempts. Make a guess";
        }
        return progressMessage;
    }


    private String stopGameAndGetMessage(String id) {
        if (repository.deleteGame(id)) {
            return STOP_GAME.message;
        }
        return NOTHING_TO_REMOVE.message;

    }

    private String sendHelp() {
        return HELP.message;
    }


    public String getId(Update update) {
        return String.valueOf(update.getMessage().getFrom().getId());
    }

    public String getText(Update update) {
        return update.getMessage().getText();
    }

}

