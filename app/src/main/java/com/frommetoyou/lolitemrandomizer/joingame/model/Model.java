package com.frommetoyou.lolitemrandomizer.joingame.model;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;

import io.reactivex.Observable;

public interface Model {
    Observable<GamePOJO> joinGame(String gameKey, String winDescription);

    Observable<Integer> addPlayerToGame();

    void removeListeners();
}
