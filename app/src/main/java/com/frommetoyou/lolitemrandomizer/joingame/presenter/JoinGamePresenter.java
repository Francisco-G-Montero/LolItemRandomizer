package com.frommetoyou.lolitemrandomizer.joingame.presenter;

import android.util.Log;

import com.frommetoyou.lolitemrandomizer.joingame.model.Model;
import com.frommetoyou.lolitemrandomizer.joingame.view.JoinGameFragment;
import com.frommetoyou.lolitemrandomizer.joingame.view.View;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class JoinGamePresenter implements Presenter {
    private View view;
    private Model model;
    private Disposable joinGameSubscription=null,playersSubscription=null;
    private final String TAG= JoinGameFragment.class.getName();

    public JoinGamePresenter(Model model) {
        this.model = model;
    }

    @Override
    public void setView(View view) {
        this.view=view;
    }

    @Override
    public void joinGame(String gameKey, String winDescription) {
        if(view != null) view.showWaitingForOpponent();
        joinGameSubscription=model.joinGame(gameKey,winDescription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GamePOJO>() {
                    @Override
                    public void onNext(@NonNull GamePOJO gamePOJO) {
                        if (view != null){
                            playersSubscription = model.addPlayerToGame()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableObserver<Integer>() {
                                        @Override
                                        public void onNext(@NonNull Integer integer) {
                                            if (integer == 2 && view != null)
                                                model.removeListeners();
                                                view.navigateToMainFragment();
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onComplete() { }
                                    });
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.v(TAG,"Conectado al juego");
                    }
                });
    }

    @Override
    public void rxJavaUnsuscribe() {
        if(joinGameSubscription!=null && !joinGameSubscription.isDisposed())joinGameSubscription.dispose();
        if(playersSubscription!=null && !playersSubscription.isDisposed())playersSubscription.dispose();
    }
}
