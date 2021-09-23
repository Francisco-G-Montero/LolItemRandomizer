package com.frommetoyou.lolitemrandomizer.main;

import com.frommetoyou.lolitemrandomizer.main.model.MainModel;
import com.frommetoyou.lolitemrandomizer.main.model.Model;
import com.frommetoyou.lolitemrandomizer.main.presenter.MainPresenter;
import com.frommetoyou.lolitemrandomizer.main.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess.Repository;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    @Provides
    public Presenter provideMainPresenter(Model model){
        return new MainPresenter(model);
    }
    @Provides
    public Model provideMainModel(Repository repository, GameRepository gameRepository){
        return new MainModel(repository,gameRepository);
    }
/*    @Provides
    public Repository provideMainRepository(LeagueOfLegendsApiService leagueOfLegendsApiService){
        return new ItemsRepository(leagueOfLegendsApiService);
    }*/
}
