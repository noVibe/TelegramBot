package com.telegram.telegrambot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateService {
    public String getUserId(Update update) {
        return String.valueOf(update.getMessage().getFrom().getId());
    }

    public String getTextMessage(Update update) {
        return update.getMessage().getText();
    }

    public String getUsername(Update update) {
        return update.getMessage().getFrom().getUserName();
    }

    public String getFirstname(Update update) {
        return update.getMessage().getFrom().getFirstName();
    }
    public String getLastname(Update update) {
        return update.getMessage().getFrom().getLastName();
    }
}
