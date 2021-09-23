package com.frommetoyou.lolitemrandomizer.joingame;

import com.frommetoyou.lolitemrandomizer.joingame.model.JoinGameModel;
import com.frommetoyou.lolitemrandomizer.joingame.model.Model;
import com.frommetoyou.lolitemrandomizer.joingame.presenter.JoinGamePresenter;
import com.frommetoyou.lolitemrandomizer.joingame.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class JoinGameModule {
    @Provides
    public Presenter provideMainPresenter(Model model){
        return new JoinGamePresenter(model);
    }
    @Provides
    public Model provideMainModel(GameRepository repository){
        return new JoinGameModel(repository);
    }

}
