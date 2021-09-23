package com.frommetoyou.lolitemrandomizer.main.view;

public interface View {
    void showSnackMessage(String message);

    void pauseMedia();

    void resumeMedia();

    void destroyMedia();
}
