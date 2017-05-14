package com.example.huangzumou.myapplication.di.component;

import android.app.Activity;

import com.example.huangzumou.myapplication.base.BaseActivity;
import com.example.huangzumou.myapplication.di.module.ActivityModule;
import com.example.huangzumou.myapplication.di.scope.ActivityScope;
import com.example.huangzumou.myapplication.ui.activity.BuyActivity;
import com.example.huangzumou.myapplication.ui.activity.InfoActivity;
import com.example.huangzumou.myapplication.ui.activity.LoginActivity;
import com.example.huangzumou.myapplication.ui.activity.RegisterActivity;


import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(InfoActivity infoActivity);
    void inject(BuyActivity buyActivity);


}
