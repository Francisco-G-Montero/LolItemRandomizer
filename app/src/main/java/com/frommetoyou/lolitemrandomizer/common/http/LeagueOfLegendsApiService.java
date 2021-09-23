package com.frommetoyou.lolitemrandomizer.common.http;

import com.frommetoyou.lolitemrandomizer.common.http.pojo.LeagueOfLegendsItems;
import com.frommetoyou.lolitemrandomizer.common.http.pojo.summonerspell.SummonerSpell;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LeagueOfLegendsApiService {
    @GET("item.json")
    Observable<LeagueOfLegendsItems> getItems();
    @GET("summoner.json")
    Observable<SummonerSpell> getSummonerSpells();
}
