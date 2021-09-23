package com.frommetoyou.lolitemrandomizer.main.presenter;

import com.frommetoyou.lolitemrandomizer.main.view.View;

public interface Presenter {
    void rxJavaUnsuscribe();

    void setView(View view);

    void pauseBackgroundMusic();

    void resumeBackgroundMusic();

    void destroyBackgroundMusic();

    void stopBackgroundMusic();

    void startBackgroundMusic();

    void deleteCurrentGame();
}
