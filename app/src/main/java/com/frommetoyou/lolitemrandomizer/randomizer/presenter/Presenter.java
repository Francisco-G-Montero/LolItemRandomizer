package com.frommetoyou.lolitemrandomizer.randomizer.presenter;

import com.frommetoyou.lolitemrandomizer.randomizer.view.View;

public interface Presenter {
    void loadItemData();

    void loadSummonerData();

    void rxJavaUnsuscribe();

    void setView(View view);

    void randomize();

    void stopRandomize();

    void clearGameData();

    void setPlayingAsHost(boolean playingAsHost);

    void setPlayingSolo(boolean playingSolo);
}
