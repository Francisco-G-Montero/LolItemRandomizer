package com.frommetoyou.lolitemrandomizer.root;

import android.app.Application;
import android.content.Context;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.FirebaseRepository;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    public GameRepository provideGameRepository(){
        return new FirebaseRepository();
    }
}
