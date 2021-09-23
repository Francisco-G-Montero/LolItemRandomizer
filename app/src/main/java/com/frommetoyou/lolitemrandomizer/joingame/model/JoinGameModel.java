package com.frommetoyou.lolitemrandomizer.joingame.model;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;

import io.reactivex.Observable;

public class JoinGameModel implements Model {
    private GameRepository gameRepository;

    public JoinGameModel(GameRepository repository) {
        this.gameRepository = repository;
    }

    @Override
    public Observable<GamePOJO> joinGame(String gameKey, String winDescription) {
        return gameRepository.joinGame(gameKey,winDescription);
    }

    @Override
    public Observable<Integer> addPlayerToGame() {
        return gameRepository.playerCountListener();
    }

    @Override
    public void removeListeners() {
        gameRepository.removeListeners();
    }
}
