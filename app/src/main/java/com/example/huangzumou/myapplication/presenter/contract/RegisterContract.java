package com.example.huangzumou.myapplication.presenter.contract;

import com.example.huangzumou.myapplication.base.BasePresenter;
import com.example.huangzumou.myapplication.base.BaseView;

/**
 * Created by huangzumou on 2017/4/22.
 */

public interface RegisterContract {


    interface View extends BaseView {

        void showResult(int ret,String msg);
        //void jumpToLogin();

    }

    interface Presenter extends BasePresenter<RegisterContract.View> {

        void register(String name,String email,String passwd,String repasswd);

    }

}
