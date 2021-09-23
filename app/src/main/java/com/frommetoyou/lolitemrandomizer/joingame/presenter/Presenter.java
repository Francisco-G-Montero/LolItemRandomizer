package com.frommetoyou.lolitemrandomizer.joingame.presenter;

import com.frommetoyou.lolitemrandomizer.joingame.view.View;

public interface Presenter {
    void setView(View view);

    void joinGame(String gameKey, String winDescription);

    void rxJavaUnsuscribe();
}
