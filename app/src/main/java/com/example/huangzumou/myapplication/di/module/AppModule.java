package com.example.huangzumou.myapplication.di.module;

import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.model.db.RealmHelper;
import com.example.huangzumou.myapplication.model.http.RetrofitHelper;
import com.example.huangzumou.myapplication.model.http.api.SsPanelApis;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper(SsPanelApis SsPanelService) {
        return new RetrofitHelper(SsPanelService);
    }

    @Provides
    @Singleton
    RealmHelper provideRealmHelper() {
        return new RealmHelper(application);
    }
}
