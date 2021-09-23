package com.frommetoyou.lolitemrandomizer.setup.presenter;

import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;
import com.frommetoyou.lolitemrandomizer.setup.view.View;

public interface Presenter {
    void setView(View view);

    void setSetupOptions(OptionsPOJO options);
}
