package com.telegram.telegrambot.service;

import com.telegram.telegrambot.model.Game;
import com.telegram.telegrambot.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.telegram.telegrambot.service.Messages.*;

@Service
public class InteractionService {

    private static final String START_COMMAND = "/start";
    private static final String STOP_COMMAND = "/stop";
    private static final String HELP_COMMAND = "/help";
    private final GameRepository repository;
    private final UpdateService updateService;
    private final GameService gameService;

    public InteractionService(GameRepository repository, UpdateService updateService, GameService gameService1) {
        this.repository = repository;
        this.updateService = updateService;
        this.gameService = gameService1;
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
        int inputNumber = Integer.parseInt(number);
        if (inputNumber < 1) {
            return NEGATIVE_VALUE.message;
        }
        Game game = repository.getGame(id);
        if (game == null) {
            return GAME_NOT_FOUND.message;
        }
        if (game.isInitialized()) {
            gameService.play(game, inputNumber);
            if (game.isOver()) {
                repository.deleteGame(id);
            }
        } else {
            gameService.initializeGame(game, inputNumber);
        }
        return game.getMessage();
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



