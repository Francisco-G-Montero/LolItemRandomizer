package com.frommetoyou.lolitemrandomizer.sharelink;

import android.content.Context;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;

import io.reactivex.Observable;

public interface ShareLinkMVP{
    interface View{
        void showSnackMessage(String message);
        void updateGameCode(String code);
        void showWaitingBar(String message);
        void disableCreateGameButton();
        void navigateToMainFragment();
    }
    interface Presenter{
        void rxJavaUnsuscribe();
        void setView(ShareLinkMVP.View view);
        void copyCode(Context context, String gameCode);
        void createGame(OptionsPOJO setupOptions, String winDescription);
        void deleteCurrentGame();
    }
    interface Model{
        Observable<String> createGame(OptionsPOJO setupOptions, String winDescription);
        Observable<Integer> addPlayerToGame();
        void deleteCurrentGame();
        void removeListeners();
    }
}
