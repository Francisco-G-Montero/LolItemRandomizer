package com.frommetoyou.lolitemrandomizer.randomizer.view;

import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;

import java.util.List;

public interface View {
    void addItemData(ItemPOJO item);

    void addSummonerData(ItemPOJO item);

    void showSnackMessage(String message);

    void clearData();

    void updateItemData(int index, ItemPOJO next);

    void updateSummonermData(int index, ItemPOJO next);

    void reproduceTickSound();

    void reproduceChosenItemsSound();

    void hideOrShowStopRandomizeButton();

    void enableUIInteractions();

    void enableFinishGameButton();

    void disableUIInteractions();

    List<ItemPOJO> getItemList();

    List<ItemPOJO> getSpellList();

    void showOpponentItems(GamePOJO gamePOJO);
}
