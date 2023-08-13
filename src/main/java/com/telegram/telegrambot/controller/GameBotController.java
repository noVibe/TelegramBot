package com.telegram.telegrambot.controller;

import com.telegram.telegrambot.service.GameService;
import com.telegram.telegrambot.service.UpdateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Controller
public class GameBotController extends TelegramLongPollingBot {
    private final GameService gameService;
    private final UpdateService updateService;

    public GameBotController(@Value("${bot.token}") String botToken, GameService gameService, UpdateService updateService) {
        super(botToken);
        this.gameService = gameService;
        this.updateService = updateService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String id = updateService.getUserId(update);
        String message = gameService.makeAnswer(update);
        sendMessage(id, message);
    }
    private void sendMessage(String id, String text) {
        SendMessage message = new SendMessage(id, text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "MyFuckingShitBot";
    }


}
