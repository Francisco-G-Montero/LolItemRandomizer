package com.frommetoyou.lolitemrandomizer.randomizer;

import com.frommetoyou.lolitemrandomizer.common.http.LeagueOfLegendsApiService;
import com.frommetoyou.lolitemrandomizer.randomizer.model.Model;
import com.frommetoyou.lolitemrandomizer.randomizer.model.RandomizerModel;
import com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess.ItemsRepository;
import com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess.Repository;
import com.frommetoyou.lolitemrandomizer.randomizer.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.randomizer.presenter.RandomizerPresenter;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RandomizerModule {
    @Provides
    public Presenter provideMainPresenter(Model model){
        return new RandomizerPresenter(model);
    }
    @Provides
    public Model provideMainModel(Repository repository, GameRepository gameRepository){
        return new RandomizerModel(repository,gameRepository);
    }
    @Provides
    public Repository provideMainRepository(LeagueOfLegendsApiService leagueOfLegendsApiService){
        return new ItemsRepository(leagueOfLegendsApiService);
    }
}
