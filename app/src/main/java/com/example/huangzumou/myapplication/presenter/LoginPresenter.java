package com.example.huangzumou.myapplication.presenter;

import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.base.RxPresenter;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.model.bean.TokenBean;
import com.example.huangzumou.myapplication.model.bean.TokenInfoBean;
import com.example.huangzumou.myapplication.model.http.response.SsPanelResponse;
import com.example.huangzumou.myapplication.presenter.contract.LoginContract;
import com.example.huangzumou.myapplication.model.http.RetrofitHelper;
import com.example.huangzumou.myapplication.model.db.RealmHelper;
import com.example.huangzumou.myapplication.util.RxUtil;
import com.example.huangzumou.myapplication.widget.CommonSubscriber;
import javax.inject.Inject;
import com.example.huangzumou.myapplication.util.LogUtil;
import java.util.List;

import io.reactivex.Scheduler;

/**
 * Created by huangzumou on 2017/4/22.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    @Inject
    public LoginPresenter(RetrofitHelper mRetrofitHelper,RealmHelper mRealHelper){
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealHelper;
        registerEvent();
    }

    private void registerEvent(){

    };

    @Override
    public RealmUserBean checkLogin() {

        RealmUserBean realmUserBean = mRealmHelper.queryLastUser();
        if(realmUserBean != null){
            App.getAppComponent().getContext().setUser(realmUserBean);
            return realmUserBean;
        }else{
            return null;
        }

    }

    @Override
    public RealmUserBean checkLogin(String email) {

        RealmUserBean realmUserBean = mRealmHelper.queryByEmail(email);
        if(realmUserBean != null){
            App.getAppComponent().getContext().setUser(realmUserBean);
            return realmUserBean;
        }else{
            return null;
        }
    }

    @Override
    public void saveToken(final String email, final TokenBean tokenBean) {
        addSubscribe(mRetrofitHelper.getTokenInfo(tokenBean.getToken())
            .compose(RxUtil.<SsPanelResponse<TokenInfoBean>>rxSchedulerHelper())
                .compose(RxUtil.<TokenInfoBean>handleSsPanelResult())
                .subscribeWith(new CommonSubscriber<TokenInfoBean>(mView){
                    @Override
                    public void onNext(TokenInfoBean tokenInfoBean) {
                        RealmUserBean realmUserBean = new RealmUserBean();
                        realmUserBean.setEmail(email);
                        realmUserBean.setUserId(tokenInfoBean.getUserId());
                        realmUserBean.setCreateTime(tokenInfoBean.getCreatTime());
                        realmUserBean.setExpireTime(tokenInfoBean.getExpireTime());
                        realmUserBean.setToken(tokenInfoBean.getToken());
                        //realmUserBean.setAutoLogin(true);
                        mRealmHelper.insertUserBean(realmUserBean);
                        mView.showError("用户已经保存");
                        App.getAppComponent().getContext().setUser(realmUserBean);
                        mView.jumpToInfo();
                    }
                })
        );
    }

    @Override
    public void login(final String email, String passwd) {

        /*
        RealmUserBean realmUserBean = this.checkLogin(email);
        if(realmUserBean != null){
            mView.showToken("realm:"+realmUserBean.getToken());
            realmUserBean.setAutoLogin(true);
            mRealmHelper.insertUserBean(realmUserBean);
            App.getAppComponent().getContext().setUser(realmUserBean);
            mView.jumpToInfo();
        }else {
        */
            addSubscribe(mRetrofitHelper.getToken(email, passwd)
                    .compose(RxUtil.<SsPanelResponse<TokenBean>>rxSchedulerHelper())
                    .compose(RxUtil.<TokenBean>handleSsPanelResult())
                    .subscribeWith(new CommonSubscriber<TokenBean>(mView){
                        @Override
                        public void onNext(TokenBean tokenBean) {
                            //LogUtil.i(tokenBean.getToken());
                            saveToken(email,tokenBean);
                            //mView.showToken(tokenBean.getToken());
                        }
                    })
            );
        //}

    }


}
