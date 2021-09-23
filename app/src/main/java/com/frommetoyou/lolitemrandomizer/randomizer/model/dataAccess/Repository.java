package com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess;

import com.frommetoyou.lolitemrandomizer.common.http.pojo.Items.Item;
import com.frommetoyou.lolitemrandomizer.common.http.pojo.summonerspell.summoner.Summoner;

import java.util.List;

import io.reactivex.Observable;

public interface Repository {
    Observable<Item> getResultData();

    Observable<Item> getResultFromCache();

    Observable<Item> getResultFromNetwork();

    Item getResultByIndex(int index);

    List<Item> getBootsTypeResult();

    List<Item> getMythicTypeResult();

    void setItemOptions(int minimumGoldPrice,boolean mustBeAlsoIncomplete,boolean mustBeComplete, String mapSelected);

    Observable<Summoner> getSummonerResult();

    Observable<Summoner> getSummonerFromCache();

    Observable<Summoner> getSummonerFromNetwork();

    Summoner getSummonerResultByIndex(int index);

}
