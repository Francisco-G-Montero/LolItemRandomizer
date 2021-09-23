package com.frommetoyou.lolitemrandomizer.randomizer.model;

import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;

import java.util.List;

import io.reactivex.Observable;

public interface Model {
    Observable<ItemPOJO> result();

    ItemPOJO getItemPOJOByIndex(int index);

    ItemPOJO getSummonerByIndex(int index);

    List<ItemPOJO> getBootsTypeItemPOJO();

    List<ItemPOJO> getMythicTypeItemPOJO();

    void setSetupOptions();

    Observable<ItemPOJO> summonerResult();

    void updateGameInRepository(List<ItemPOJO> itemList, List<ItemPOJO> spellList, boolean playingAsHost);

    Observable<GamePOJO> opponentDataListener();

    void clearGameData();
}
