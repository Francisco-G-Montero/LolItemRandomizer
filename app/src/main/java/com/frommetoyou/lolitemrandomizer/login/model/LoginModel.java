package com.frommetoyou.lolitemrandomizer.login.model;

import com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess.Repository;

public class LoginModel implements Model {
    private Repository repository;

    public LoginModel(Repository repository) {
        this.repository = repository;
    }
}
