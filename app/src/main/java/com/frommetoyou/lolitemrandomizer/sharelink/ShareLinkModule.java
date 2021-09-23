package com.frommetoyou.lolitemrandomizer.sharelink;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GameRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ShareLinkModule {
    @Provides
    public ShareLinkMVP.Presenter provideMainPresenter(ShareLinkMVP.Model model){
        return new ShareLinkPresenter(model);
    }
    @Provides
    public ShareLinkMVP.Model provideMainModel(GameRepository shareLinkRepository){
        return new ShareLinkModel(shareLinkRepository);
    }
}
