package com.frommetoyou.lolitemrandomizer.randomizer.presenter;

import android.util.Log;

import com.frommetoyou.lolitemrandomizer.randomizer.model.Model;
import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;
import com.frommetoyou.lolitemrandomizer.randomizer.view.View;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class RandomizerPresenter implements Presenter {
    private final String TAG = RandomizerPresenter.class.getName();
    private View view;
    private Model model;
    private Disposable itemSubscription = null, subscriptionHandler, summonerSubscription = null, opponentSubscription=null;
    private int itemPOJOSize = 0, summonerPOJOSize = 0;
    private Random random = new Random();
    private int index = 0;
    private boolean mustStopRandomize = false, playingAsHost=true, playingSolo=false;
    private int period = 0;

    public RandomizerPresenter(Model model) {
        this.model = model;
    }

    @Override
    public void setPlayingAsHost(boolean playingAsHost) {
        this.playingAsHost=playingAsHost;
    }

    @Override
    public void setPlayingSolo(boolean playingSolo) {
        this.playingSolo=playingSolo;
    }

    @Override
    public void loadItemData() {
        itemPOJOSize = 0;
        model.setSetupOptions();
        itemSubscription = model.result()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ItemPOJO>() {
                    @Override
                    public void onNext(@NonNull ItemPOJO item) {
                        if (view != null) view.addItemData(item);
                        itemPOJOSize++;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        if (view != null)
                            view.showSnackMessage("Error al descargar los items de lol");
                    }

                    @Override
                    public void onComplete() {
                        //if (view != null) view.showSnackMessage("Items descargados con éxito");
                        //loadSummonerData();
                        opponentSubscription=model.opponentDataListener()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<GamePOJO>() {
                                    @Override
                                    public void onNext(@NonNull GamePOJO gamePOJO) {
                                        view.showOpponentItems(gamePOJO);
                                        Log.v(TAG,"Partida de: "+gamePOJO.getOwner().getName());
                                    }
                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.v(TAG,e.toString());
                                    }

                                    @Override
                                    public void onComplete() { }}
                                    );
                    }
                });
    }

    @Override
    public void loadSummonerData() {
        view.disableUIInteractions();
        summonerPOJOSize = 0;
        summonerSubscription = model.summonerResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ItemPOJO>() {
                    @Override
                    public void onNext(@NonNull ItemPOJO spellPOJO) {
                        if (view != null) view.addSummonerData(spellPOJO);
                        summonerPOJOSize++;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        if (view != null)
                            view.showSnackMessage("Error al descargar los summoners de lol");
                    }

                    @Override
                    public void onComplete() {
                        // if (view != null) view.showSnackMessage("Summoners descargados con éxito");
                        if (view != null) {
                            Disposable descartable = Observable.timer(800, TimeUnit.MILLISECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(aLong -> {
                                        view.enableUIInteractions();
                                        this.dispose();
                                    });
                        }
                    }
                });
    }

    @Override
    public void rxJavaUnsuscribe() {
        if (itemSubscription != null && !itemSubscription.isDisposed()) itemSubscription.dispose();
        if (summonerSubscription != null && !summonerSubscription.isDisposed()) summonerSubscription.dispose();
        if (opponentSubscription != null && !opponentSubscription.isDisposed()) opponentSubscription.dispose();
        if (subscriptionHandler != null && !subscriptionHandler.isDisposed()) subscriptionHandler.dispose();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void randomize() {
        if (view != null) {
            view.clearData();
            view.hideOrShowStopRandomizeButton();
            fillData();
            mustStopRandomize = false;
            period = 25;
            if (subscriptionHandler == null || subscriptionHandler.isDisposed())
                randomizeSuscription();
        }
    }

    private void randomizeSuscription() {
        subscriptionHandler = Observable.interval(period, period, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                            updateItems();
                            updateSummoners();
                            view.reproduceTickSound();
                            if (mustStopRandomize) {
                                if (period >= 1500) {
                                    this.subscriptionHandler.dispose();
                                    view.reproduceChosenItemsSound();
                                    if (!playingSolo && view.getItemList()!=null && view.getSpellList() != null)
                                        model.updateGameInRepository(view.getItemList(), view.getSpellList(), playingAsHost);
                                    view.disableUIInteractions();
                                    view.enableFinishGameButton();
                                } else {
                                    period += period;
                                    this.subscriptionHandler.dispose();
                                    randomizeSuscription();
                                }
                            }
                        }
                );
    }


    @Override
    public void stopRandomize() {
        view.hideOrShowStopRandomizeButton();
        mustStopRandomize = true;
    }

    private void fillData() {

        for (int i = 0; i < 6; i++) {
            index = random.nextInt(itemPOJOSize);
            view.addItemData(model.getItemPOJOByIndex(index));
        }
        for (int i = 0; i < 2; i++) {
            index = random.nextInt(summonerPOJOSize);
            view.addSummonerData(model.getSummonerByIndex(index));
        }
    }

    private void updateItems() {
        int bootsIndex;
        List<Integer> remainingIndex = new ArrayList<>();
        bootsIndex = random.nextInt(6);

        for (int i = 0; i < 5; i++) {
            int aux = random.nextInt(6);
            if (!remainingIndex.contains(aux) && aux != bootsIndex)
                remainingIndex.add(aux);
            else i--;
        }
        List<ItemPOJO> bootsItems = model.getBootsTypeItemPOJO();
        index = random.nextInt(bootsItems.size());
        view.updateItemData(bootsIndex, bootsItems.get(index));

        List<ItemPOJO> mythicItems = model.getMythicTypeItemPOJO();

        List<ItemPOJO> remainingItems = new ArrayList<>();
        boolean mythicAdded = false;

        for (int i = 0; i < 5; i++) {
            index = random.nextInt(itemPOJOSize);
            ItemPOJO itemPOJO = model.getItemPOJOByIndex(index);
            if (mythicItems.contains(itemPOJO) && !mythicAdded) {
                mythicAdded = true;
                remainingItems.add(itemPOJO);
                view.updateItemData(remainingIndex.get(i), itemPOJO);
            } else if (!bootsItems.contains(itemPOJO) && !mythicItems.contains(itemPOJO) && (!remainingItems.contains(itemPOJO) || itemPOJO.getName().equals(ItemPOJO.COMODIN))) {
                remainingItems.add(itemPOJO);
                view.updateItemData(remainingIndex.get(i), itemPOJO);
            } else i--;
        }
    }

    private void updateSummoners() {
        List<ItemPOJO> remainingItems = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            index = random.nextInt(summonerPOJOSize);
            ItemPOJO itemPOJO = model.getSummonerByIndex(index);
            if ((!remainingItems.contains(itemPOJO) || itemPOJO.getName().equals(ItemPOJO.COMODIN))) {
                remainingItems.add(itemPOJO);
                view.updateSummonermData(i, itemPOJO);
            } else i--;
        }
    }

    @Override
    public void clearGameData() {
        if (!playingSolo)
        model.clearGameData();
    }
}
