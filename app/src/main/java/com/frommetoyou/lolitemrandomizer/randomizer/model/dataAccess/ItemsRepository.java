package com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess;

import com.frommetoyou.lolitemrandomizer.common.http.LeagueOfLegendsApiService;
import com.frommetoyou.lolitemrandomizer.common.http.pojo.Items.Item;
import com.frommetoyou.lolitemrandomizer.common.http.pojo.LeagueOfLegendsItems;
import com.frommetoyou.lolitemrandomizer.common.http.pojo.summonerspell.SummonerSpell;
import com.frommetoyou.lolitemrandomizer.common.http.pojo.summonerspell.summoner.Summoner;
import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class ItemsRepository implements Repository {
    private final LeagueOfLegendsApiService leagueOfLegendsApiService;
    private final List<Item> itemsList;
    private final List<Summoner> summonerSpellList;
    private int minimumGoldPrice;
    private boolean mustBeAlsoIncomplete, mustBeComplete;
    private String mapSelected=ItemPOJO.SPELL_MODE_CLASSIC;

    private long lastTimestamp; //para saber cuando fue la ultima vez que se refrescó la cache
    private static final long CACHE_LIFETIME = 20 * 1000 * 60; //la cache durará 20min

    public ItemsRepository(LeagueOfLegendsApiService leagueOfLegendsApiService) {
        this.leagueOfLegendsApiService = leagueOfLegendsApiService;
        this.itemsList = new ArrayList<>();
        this.summonerSpellList = new ArrayList<>();
        this.lastTimestamp = System.currentTimeMillis();
        this.minimumGoldPrice=1000;
    }

    public boolean isUpdated(){
        return (System.currentTimeMillis()-lastTimestamp)<CACHE_LIFETIME;
    }

    @Override
    public Observable<Item> getResultData() {
        return getResultFromCache().switchIfEmpty(getResultFromNetwork());
    }

    @Override
    public Observable<Item> getResultFromCache() {
        if (isUpdated()){
            return Observable.fromIterable(itemsList);
        }else{
            lastTimestamp=System.currentTimeMillis();
            itemsList.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Item> getResultFromNetwork() {
        Observable<LeagueOfLegendsItems> leagueOfLegendsItemsObservable=leagueOfLegendsApiService.getItems();
        return leagueOfLegendsItemsObservable.concatMap(new Function<LeagueOfLegendsItems, Observable<Item>>() {
            @Override
            public Observable<Item> apply(@NonNull LeagueOfLegendsItems leagueOfLegendsItems){
                if ( leagueOfLegendsItems == null || leagueOfLegendsItems.getItems() == null )
                    return Observable.just(new Item());
                return Observable.fromIterable(new ArrayList<Item>(leagueOfLegendsItems.getItems().values()));
            }
        }).filter(item ->
                ((item.getInto().size()==0)==mustBeComplete || (item.getInto().size()>0)==mustBeAlsoIncomplete) &&
                    item.getRequiredChampion()==null &&
                    item.getInStore() &&
                    (!item.getConsumed()||item.getConsumed()==mustBeAlsoIncomplete) &&
                     (!item.getTags().contains(ItemPOJO.TYPE_JUNGLE) || item.getTags().contains(ItemPOJO.TYPE_LANE)) &&
                    (item.getGold().getTotal()>=minimumGoldPrice || item.getTags().contains(ItemPOJO.TYPE_BOOTS))
        ).doOnNext(item -> itemsList.add(item));
    }

    @Override
    public Item getResultByIndex(int index) {
        if(itemsList!=null&&!itemsList.isEmpty()) {
            return itemsList.get(index);
        }else return null;
    }

    @Override
    public List<Item> getBootsTypeResult() {
        List<Item> bootsItems=new ArrayList<>();
        if(itemsList!=null&&!itemsList.isEmpty()) {
            for(Item item : itemsList){
                if(item.getTags().contains(ItemPOJO.TYPE_BOOTS))
                    bootsItems.add(item);
            }
        }
        return bootsItems;
    }
    @Override
    public List<Item> getMythicTypeResult() {
        List<Item> mythicItems=new ArrayList<>();
        if(itemsList!=null&&!itemsList.isEmpty()) {
            for(Item item : itemsList){
                if(item.getDescription().contains(ItemPOJO.TYPE_MYTHIC))
                    mythicItems.add(item);
            }
        }return mythicItems;
    }

    @Override
    public void setItemOptions(int minimumGoldPrice,boolean mustBeIncomplete,boolean mustBeComplete, String mapSelected) {
        this.minimumGoldPrice=minimumGoldPrice;
        this.mustBeAlsoIncomplete =mustBeIncomplete;
        this.mustBeComplete =mustBeComplete;
        this.mapSelected=mapSelected;
    }

    @Override
    public Observable<Summoner> getSummonerResult() {
        return getSummonerFromCache().switchIfEmpty(getSummonerFromNetwork());
    }

    @Override
    public Observable<Summoner> getSummonerFromCache() {
        if (isUpdated()){
            return Observable.fromIterable(summonerSpellList);
        }else{
            lastTimestamp=System.currentTimeMillis();
            itemsList.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Summoner> getSummonerFromNetwork() {
        Observable<SummonerSpell> summonerSpellsObservable=leagueOfLegendsApiService.getSummonerSpells();
        return summonerSpellsObservable.concatMap(new Function<SummonerSpell, Observable<Summoner>>() {
            @Override
            public Observable<Summoner> apply(@NonNull SummonerSpell summonerSpell){
                if ( summonerSpell == null || summonerSpell.getSummoners() == null )
                    return Observable.just(new Summoner());
                else
                  return Observable.fromIterable(summonerSpell.getSummoners().values());
            }
        }).filter(summoner ->
                summoner.getModes().contains(mapSelected)
        ).doOnNext(summoner -> summonerSpellList.add(summoner));
    }

    @Override
    public Summoner getSummonerResultByIndex(int index) {
        if(summonerSpellList!=null&&!summonerSpellList.isEmpty()) {
            return summonerSpellList.get(index);
        }else return null;
    }


}
