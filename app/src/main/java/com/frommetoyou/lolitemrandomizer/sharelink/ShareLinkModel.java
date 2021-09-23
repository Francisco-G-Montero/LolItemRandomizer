package com.frommetoyou.lolitemrandomizer.sharelink;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.PlayerPOJO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;

public class ShareLinkModel implements ShareLinkMVP.Model {
    private GameRepository gameRepository;
    private FirebaseUser firebaseUser;

    public ShareLinkModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public Observable<String> createGame(OptionsPOJO setupOptions, String winDescription) {
        GamePOJO gamePOJO=new GamePOJO();
        gamePOJO.setSetupOptions(setupOptions);
        gamePOJO.setOwner(new PlayerPOJO());
        gamePOJO.getOwner().setWinDescription(winDescription);
        return gameRepository.createGame(gamePOJO);
    }

    @Override
    public Observable<Integer> addPlayerToGame() {
        return gameRepository.playerCountListener();
    }

    @Override
    public void deleteCurrentGame() {
        gameRepository.deleteCurrentGame();
    }

    @Override
    public void removeListeners() {
        gameRepository.removeListeners();
    }
}
