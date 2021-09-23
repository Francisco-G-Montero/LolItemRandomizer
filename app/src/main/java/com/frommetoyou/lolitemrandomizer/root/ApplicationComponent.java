package com.frommetoyou.lolitemrandomizer.root;

import com.frommetoyou.lolitemrandomizer.joingame.view.JoinGameFragment;
import com.frommetoyou.lolitemrandomizer.joingame.JoinGameModule;
import com.frommetoyou.lolitemrandomizer.login.view.LoginFragment;
import com.frommetoyou.lolitemrandomizer.login.LoginModule;
import com.frommetoyou.lolitemrandomizer.main.view.MainActivity;
import com.frommetoyou.lolitemrandomizer.common.http.LeagueOfLegendsApiModule;
import com.frommetoyou.lolitemrandomizer.main.MainModule;
import com.frommetoyou.lolitemrandomizer.randomizer.view.MainFragment;
import com.frommetoyou.lolitemrandomizer.randomizer.RandomizerModule;
import com.frommetoyou.lolitemrandomizer.setup.view.SetupFragment;
import com.frommetoyou.lolitemrandomizer.setup.SetupModule;
import com.frommetoyou.lolitemrandomizer.sharelink.ShareLinkFragment;
import com.frommetoyou.lolitemrandomizer.sharelink.ShareLinkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (
        modules = {
                ApplicationModule.class,
                LeagueOfLegendsApiModule.class,
                MainModule.class,
                RandomizerModule.class,
                LoginModule.class,
                ShareLinkModule.class,
                JoinGameModule.class,
                SetupModule.class,
        })
public interface ApplicationComponent {
    void inject(MainActivity target);
    void inject(MainFragment target);
    void inject(LoginFragment target);
    void inject(ShareLinkFragment target);
    void inject(JoinGameFragment target);
    void inject(SetupFragment target);
}
