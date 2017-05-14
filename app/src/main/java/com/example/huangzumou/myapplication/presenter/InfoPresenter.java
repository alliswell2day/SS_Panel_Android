package com.example.huangzumou.myapplication.presenter;

import android.content.Intent;
import android.util.Base64;


import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.base.RxPresenter;
import com.example.huangzumou.myapplication.model.bean.NodeBean;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.model.bean.UserInfoBean;
import com.example.huangzumou.myapplication.model.db.RealmHelper;
import com.example.huangzumou.myapplication.model.http.RetrofitHelper;
import com.example.huangzumou.myapplication.model.http.response.SsPanelResponse;
import com.example.huangzumou.myapplication.presenter.contract.InfoContract;
import com.example.huangzumou.myapplication.socks.core.LocalVpnService;
import com.example.huangzumou.myapplication.util.LogUtil;
import com.example.huangzumou.myapplication.util.RxUtil;
import com.example.huangzumou.myapplication.widget.CommonSubscriber;

import javax.inject.Inject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by huangzumou on 2017/4/22.
 */

public class InfoPresenter extends RxPresenter<InfoContract.View> implements InfoContract.Presenter, LocalVpnService.onStatusChangedListener {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealHelper;


    @Inject
    public InfoPresenter(RetrofitHelper mRetrofitHelper,RealmHelper mRealHelper){
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealHelper = mRealHelper;
    }

    @Override
    public void getUserInfo() {

        //get user token and other info form Realm(App)

        RealmUserBean realmUserBean = App.getAppComponent().getContext().getUser();

        if(realmUserBean != null){
            addSubscribe(mRetrofitHelper.getUserInfo(realmUserBean.getUserId(), realmUserBean.getToken())
                    .compose(RxUtil.<SsPanelResponse<UserInfoBean>>rxSchedulerHelper())
                    .compose(RxUtil.<UserInfoBean>handleSsPanelResult())
                    .subscribeWith(new CommonSubscriber<UserInfoBean>(mView){
                        @Override
                        public void onNext(UserInfoBean userInfoBean) {

                            mView.updateUserInfo(userInfoBean);
                            App.getAppComponent().getContext().setUserInfo(userInfoBean);
                        }
                    })
            );

        }else {
            mView.showError("未取得有效Token信息");
        }

    }

    @Override
    public void getNodeInfo() {

        RealmUserBean realmUserBean = App.getAppComponent().getContext().getUser();

        if(realmUserBean != null){
            addSubscribe(mRetrofitHelper.getNodeInfo(realmUserBean.getToken())
                    .compose(RxUtil.<SsPanelResponse<List<NodeBean>>>rxSchedulerHelper())
                    .compose(RxUtil.<List<NodeBean>>handleSsPanelResult())
                    .subscribeWith(new CommonSubscriber<List<NodeBean>>(mView){
                        @Override
                        public void onNext(List<NodeBean> mList) {

                            mView.updateNodeInfo(mList);
                        }
                    })
            );

        }else {
            mView.showError("未取得有效Token信息");
        }

    }

    @Override
    public void onStatusChanged(String status, Boolean isRunning) {

        if(mView != null){
            mView.setConnectButtonState(isRunning);
            mView.showError(status);
        }else {
            LogUtil.i("mView is null");
        }
    }



    @Override
    public void onLogReceived(String logString) {
        //mView.showLog(logString);
    }

    @Override
    public void deleteUser() {
        RealmUserBean realmUserBean = App.getAppComponent().getContext().getUser();
        if(realmUserBean != null){
            mView.showError("用户已登出");
            mRealHelper.deleteUser(realmUserBean.getEmail());
        }
    }
}
