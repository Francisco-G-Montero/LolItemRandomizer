package com.frommetoyou.lolitemrandomizer.setup;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;
import com.frommetoyou.lolitemrandomizer.setup.model.Model;
import com.frommetoyou.lolitemrandomizer.setup.model.SetupModel;
import com.frommetoyou.lolitemrandomizer.setup.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.setup.presenter.SetupPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SetupModule {
    @Provides
    public Presenter provideMainPresenter(Model model){
        return new SetupPresenter(model);
    }
    @Provides
    public Model provideMainModel(GameRepository repository){
        return new SetupModel(repository);
    }
}
