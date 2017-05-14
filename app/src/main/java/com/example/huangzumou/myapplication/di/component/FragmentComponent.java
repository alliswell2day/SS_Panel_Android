package com.example.huangzumou.myapplication.di.component;

import android.app.Activity;

import com.example.huangzumou.myapplication.di.module.FragmentModule;
import com.example.huangzumou.myapplication.di.scope.FragmentScope;


import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();


}
