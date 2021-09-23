package com.frommetoyou.lolitemrandomizer.root;

import android.app.Application;

import com.frommetoyou.lolitemrandomizer.common.http.LeagueOfLegendsApiModule;
import com.frommetoyou.lolitemrandomizer.joingame.JoinGameModule;
import com.frommetoyou.lolitemrandomizer.login.LoginModule;
import com.frommetoyou.lolitemrandomizer.main.MainModule;
import com.frommetoyou.lolitemrandomizer.randomizer.RandomizerModule;
import com.frommetoyou.lolitemrandomizer.setup.SetupModule;
import com.frommetoyou.lolitemrandomizer.sharelink.ShareLinkModule;

public class App extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .leagueOfLegendsApiModule(new LeagueOfLegendsApiModule())
                .mainModule(new MainModule())
                .randomizerModule(new RandomizerModule())
                .loginModule(new LoginModule())
                .shareLinkModule(new ShareLinkModule())
                .joinGameModule(new JoinGameModule())
                .setupModule(new SetupModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
