package com.frommetoyou.lolitemrandomizer.main.presenter;

import com.frommetoyou.lolitemrandomizer.main.model.Model;
import com.frommetoyou.lolitemrandomizer.main.view.View;

import io.reactivex.disposables.Disposable;

public class MainPresenter implements Presenter {
    private View view;
    private Model model;
    private Disposable suscription=null;
    private boolean playBackgroundMusic=true;

    public MainPresenter(Model model) {
        this.model = model;
    }

    @Override
    public void rxJavaUnsuscribe() {
        if (suscription!=null && !suscription.isDisposed()) suscription.dispose();
    }

    @Override
    public void setView(View view) {
        this.view=view;
    }

    @Override
    public void pauseBackgroundMusic() {
        view.pauseMedia();
    }

    @Override
    public void resumeBackgroundMusic() {
        if(playBackgroundMusic) view.resumeMedia();
    }

    @Override
    public void destroyBackgroundMusic() {
        view.destroyMedia();
    }

    @Override
    public void stopBackgroundMusic() {
        playBackgroundMusic=false;
        pauseBackgroundMusic();
    }

    @Override
    public void startBackgroundMusic() {
        playBackgroundMusic=true;
        resumeBackgroundMusic();
    }

    @Override
    public void deleteCurrentGame() {
        model.deleteCurrentGame();
    }
}
