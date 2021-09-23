package com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories;

import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;

import java.util.List;

import io.reactivex.Observable;

public interface GameRepository {

    Observable<GamePOJO> joinGame(String gameKey, String winDescription);

    Observable<GamePOJO> retrieveGameListener();

    Observable<String> createGame(GamePOJO game);

    Observable<Integer> playerCountListener();

    void addPlayerToTheGame();

    void clearGameData();

    void deleteCurrentGame();

    OptionsPOJO getGameOptions();

    void updateGame(List<ItemPOJO> itemList, List<ItemPOJO> spellList, boolean playingAsHost);

    void removeListeners();

    void setGameOptions(OptionsPOJO options);


}
