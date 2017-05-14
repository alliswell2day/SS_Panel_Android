package com.example.huangzumou.myapplication.di.component;

import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.di.module.AppModule;
import com.example.huangzumou.myapplication.di.module.HttpModule;
import com.example.huangzumou.myapplication.model.db.RealmHelper;
import com.example.huangzumou.myapplication.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    RealmHelper realmHelper();    //提供数据库帮助类
}
