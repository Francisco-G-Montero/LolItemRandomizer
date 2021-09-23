package com.frommetoyou.lolitemrandomizer.login;

import com.frommetoyou.lolitemrandomizer.login.model.LoginModel;
import com.frommetoyou.lolitemrandomizer.login.model.Model;
import com.frommetoyou.lolitemrandomizer.login.presenter.LoginPresenter;
import com.frommetoyou.lolitemrandomizer.login.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.randomizer.model.dataAccess.Repository;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    @Provides
    public Presenter provideMainPresenter(Model model){
        return new LoginPresenter(model);
    }
    @Provides
    public Model provideMainModel(Repository repository){
        return new LoginModel(repository);
    }
/*    @Provides
    public Repository provideMainRepository(LeagueOfLegendsApiService leagueOfLegendsApiService){
        return new ItemsRepository(leagueOfLegendsApiService);
    }*/
}
