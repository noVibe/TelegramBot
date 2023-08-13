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
    private final GameRepository repository;
    private final UpdateService updateService;

    public GameService(GameRepository repository, UpdateService updateService) {
        this.repository = repository;
        this.updateService = updateService;
    }

    public String makeAnswer(Update update) {
        String id = updateService.getUserId(update);
        String command = updateService.getTextMessage(update);
        return switch (command) {
            case START_COMMAND -> startGameAndGetMessage(id);
            case STOP_COMMAND -> stopGameAndGetMessage(id);
            case HELP_COMMAND -> sendHelp();
            default -> playGameAndGetProgressMessage(id, command);
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

}



