package com.frommetoyou.lolitemrandomizer.main.model;

import com.frommetoyou.lolitemrandomizer.common.http.pojo.Items.Item;
import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;
import com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess.Repository;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class MainModel implements Model {
    private Repository repository;
    private GameRepository gameRepository;

    public MainModel(Repository repository, GameRepository gameRepository) {
        this.repository = repository;
        this.gameRepository = gameRepository;
    }


    @Override
    public Observable<ItemPOJO> result() {
        return repository.getResultData().flatMap(new Function<Item, Observable<ItemPOJO>>() {
            @Override
            public Observable<ItemPOJO> apply(@NonNull Item item){
                return Observable.just(new ItemPOJO(item.getName(),item.getImage().getFull()));
            }
        });
    }

    @Override
    public void deleteCurrentGame() {
        gameRepository.deleteCurrentGame();
    }
}

