package com.frommetoyou.lolitemrandomizer.randomizer.model;

import android.util.Log;

import com.frommetoyou.lolitemrandomizer.common.http.pojo.Items.Item;
import com.frommetoyou.lolitemrandomizer.common.http.pojo.summonerspell.summoner.Summoner;
import com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess.Repository;
import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class RandomizerModel implements Model {
    private Repository repository;
    private GameRepository gameRepository;

    public RandomizerModel(Repository repository,GameRepository gameRepository) {
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
    public ItemPOJO getItemPOJOByIndex(int index) {
        Item item = repository.getResultByIndex(index);
        return new ItemPOJO(item.getName().equals(ItemPOJO.TYPE_ESPATULA)?ItemPOJO.COMODIN:item.getName(),item.getImage().getFull());
    }

    @Override
    public ItemPOJO getSummonerByIndex(int index) {
        Summoner item = repository.getSummonerResultByIndex(index);
        ItemPOJO itemPOJO=new ItemPOJO(item.getName(),"imagenNula↓");
        itemPOJO.setImageURL(ItemPOJO.BASE_SPELL_IMAGE_URL+item.getImage().getFull());
        return itemPOJO;
    }

    @Override
    public List<ItemPOJO> getBootsTypeItemPOJO() {
        List<ItemPOJO> bootsItemPOJO=new ArrayList<>();
        for(Item item:repository.getBootsTypeResult()){
            bootsItemPOJO.add(new ItemPOJO(item.getName(),item.getImage().getFull()));
        }
        return bootsItemPOJO;
    }

    @Override
    public List<ItemPOJO> getMythicTypeItemPOJO() {
        List<ItemPOJO> mythicsItemPOJO=new ArrayList<>();
        for(Item item:repository.getMythicTypeResult()){
            mythicsItemPOJO.add(new ItemPOJO(item.getName(),item.getImage().getFull()));
        }
        return mythicsItemPOJO;
    }
    //TODO esta funcion tiene que ser llamada para configurar el itemRepo desde la firebaseRepo

    @Override
    public void setSetupOptions() {
        boolean mustBeComplete=true,mustIncomplete=true;
        OptionsPOJO gameOptions=gameRepository.getGameOptions();
        int minimumGoldPrice=50;
        if (gameOptions.getItemPool().equals(ItemPOJO.ITEM_POOL_BOTH)){
            mustBeComplete=true;
            mustIncomplete=true;
        }else if (gameOptions.getItemPool().equals(ItemPOJO.ITEM_POOL_ONLY_COMPLETE)){
            mustBeComplete=true;
            mustIncomplete=false;
            minimumGoldPrice=1000;
        }else if (gameOptions.getItemPool().equals(ItemPOJO.ITEM_ONLY_INCOMPLETE)){
            mustIncomplete=true;
            mustBeComplete=false;
        }
        Log.v("MISBOUNDS"," "+gameRepository.getGameOptions().getMapSelected());

        repository.setItemOptions(minimumGoldPrice,mustIncomplete,mustBeComplete,gameOptions.getMapSelected());
    }

    @Override
    public Observable<ItemPOJO> summonerResult() {
        return repository.getSummonerResult().flatMap(new Function<Summoner, Observable<ItemPOJO>>() {
            @Override
            public Observable<ItemPOJO> apply(@NonNull Summoner summonerSpell){
                ItemPOJO itemPOJO=new ItemPOJO(summonerSpell.getName(),"imagenNula↓");
                itemPOJO.setImageURL(ItemPOJO.BASE_SPELL_IMAGE_URL+summonerSpell.getImage().getFull());
                return Observable.just(itemPOJO);
            }
        });
    }

    @Override
    public void updateGameInRepository(List<ItemPOJO> itemList, List<ItemPOJO> spellList, boolean playingAsHost) {
        gameRepository.updateGame(itemList,spellList,playingAsHost);
    }

    @Override
    public Observable<GamePOJO> opponentDataListener() {
        return gameRepository.retrieveGameListener();
    }

    @Override
    public void clearGameData() {
        gameRepository.deleteCurrentGame();
    }
}
