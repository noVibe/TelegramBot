package com.telegram.telegrambot.repository;

import com.telegram.telegrambot.model.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class GameRepository {
    private final Map<String, Game> activeGames = new HashMap<>();

    public void addGame(String id, Game game) {
        activeGames.put(id, game);
    }

    public Game getGame(String id) {
        return activeGames.get(id);
    }
    public boolean deleteGame(String id) {
        return null != activeGames.remove(id);
    }
}
