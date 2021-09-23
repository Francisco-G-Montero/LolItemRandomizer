package com.frommetoyou.lolitemrandomizer.setup.presenter;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;
import com.frommetoyou.lolitemrandomizer.setup.model.Model;
import com.frommetoyou.lolitemrandomizer.setup.view.View;

import io.reactivex.disposables.Disposable;

public class SetupPresenter implements Presenter {
    private View view;
    private Model model;
    private Disposable createGameSubscription;

    public SetupPresenter(Model model) {
        this.model = model;
    }

    @Override
    public void setView(View view) {
        this.view=view;
    }

    @Override
    public void setSetupOptions(OptionsPOJO options) {
        model.setSetupOptions(options);
    }
}
