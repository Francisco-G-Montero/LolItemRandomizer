package com.frommetoyou.lolitemrandomizer.setup.model;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;

public class SetupModel implements Model {
    private GameRepository gameRepository;

    public SetupModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    @Override
    public void setSetupOptions(OptionsPOJO options) {
        gameRepository.setGameOptions(options);
    }
}
