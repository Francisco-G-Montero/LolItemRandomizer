package com.frommetoyou.lolitemrandomizer.login.presenter;

import com.frommetoyou.lolitemrandomizer.login.model.Model;
import com.frommetoyou.lolitemrandomizer.login.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.login.view.View;

public class LoginPresenter implements Presenter {
    private View view;
    private Model model;

    public LoginPresenter(Model model) {
        this.model = model;
    }

    @Override
    public void setView(View view) {
        this.view=view;
    }

    @Override
    public void loginUser() {
       // view.showMessage("Login existoso!");
        view.navigateToSetupFragment();
    }

}
