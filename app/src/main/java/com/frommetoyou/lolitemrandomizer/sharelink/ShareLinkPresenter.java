package com.frommetoyou.lolitemrandomizer.sharelink;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.frommetoyou.lolitemrandomizer.R;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ShareLinkPresenter implements ShareLinkMVP.Presenter {

    private ShareLinkMVP.Model model;
    private ShareLinkMVP.View view;
    private Disposable createGameSubscription = null, playersSubscription = null;

    public ShareLinkPresenter(ShareLinkMVP.Model model) {
        this.model = model;
    }

    @Override
    public void rxJavaUnsuscribe() {
        if (createGameSubscription != null && !createGameSubscription.isDisposed())
            createGameSubscription.dispose();
        if (playersSubscription != null && !playersSubscription.isDisposed())
            playersSubscription.dispose();
    }

    @Override
    public void setView(ShareLinkMVP.View view) {
        this.view = view;
    }

    @Override
    public void createGame(OptionsPOJO setupOptions, String winDescription) {
        if (view != null) {
            view.showWaitingBar("Creando partida...");
            view.disableCreateGameButton();
        }
        createGameSubscription = model.createGame(setupOptions,winDescription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String gameKey) {
                        if (view != null) {
                            view.showWaitingBar("Partida creada, esperando oponente...");
                            view.updateGameCode(gameKey);
                        }
                        playersSubscription = model.addPlayerToGame()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<Integer>() {
                                    @Override
                                    public void onNext(@NonNull Integer integer) {
                                        Log.v("OPPONENT","players: "+integer);
                                        if (integer == 2 && view != null){
                                            model.removeListeners();
                                            view.navigateToMainFragment();
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onComplete() { }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void deleteCurrentGame() {
        model.deleteCurrentGame();
    }

    @Override
    public void copyCode(Context context, String gameCode) {
        String message;
        if (!gameCode.equals(context.getString(R.string.share_link_code_example))) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(context.getString(R.string.app_name), gameCode);
            clipboard.setPrimaryClip(clip);
            message = "El código ha sido copiado con éxito";
        } else {
            message = "El código aún no ha sido cargado";
        }
        if (view != null) view.showSnackMessage(message);
    }

}
