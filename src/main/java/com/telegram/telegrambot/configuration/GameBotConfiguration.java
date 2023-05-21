package com.telegram.telegrambot.configuration;

import com.telegram.telegrambot.controller.GameBotController;
import com.telegram.telegrambot.model.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;

@Configuration
public class GameBotConfiguration {
    @Bean
    public TelegramBotsApi telegramBotsApi(GameBotController gameBotController) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(gameBotController);
        return api;
    }
    @Bean
    public HashMap<String, Game> gameHashMap() {
        return new HashMap<>();
    }
}
