package com.example.huangzumou.myapplication.presenter.contract;

import com.example.huangzumou.myapplication.base.BasePresenter;
import com.example.huangzumou.myapplication.base.BaseView;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.model.bean.TokenBean;


/**
 * Created by huangzumou on 2017/4/22.
 */

public interface LoginContract {

    interface View extends BaseView{

        //void showToken(String token);
        void jumpToInfo();

    }

    interface Presenter extends BasePresenter<View>{

        RealmUserBean checkLogin();

        RealmUserBean checkLogin(String email);

        void saveToken(String email ,TokenBean tokenBean);

        void login(String email,String passwd);

    }

}
