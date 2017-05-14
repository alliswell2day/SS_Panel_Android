package com.example.huangzumou.myapplication.base;

/**
 * Created by huangzumou on 2017/4/22.
 */

public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void detachView();
}
