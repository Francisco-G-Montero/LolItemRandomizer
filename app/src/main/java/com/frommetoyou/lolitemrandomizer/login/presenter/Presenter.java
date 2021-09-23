package com.frommetoyou.lolitemrandomizer.login.presenter;

import com.frommetoyou.lolitemrandomizer.login.view.View;

public interface Presenter {
    void setView(View view);

    void loginUser();
}
