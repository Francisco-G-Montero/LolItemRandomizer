package com.frommetoyou.lolitemrandomizer.main.model;

import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;

import io.reactivex.Observable;

public interface Model {
    Observable<ItemPOJO> result();

    void deleteCurrentGame();
}
