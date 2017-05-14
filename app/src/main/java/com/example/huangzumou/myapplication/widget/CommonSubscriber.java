package com.example.huangzumou.myapplication.widget;

import android.text.TextUtils;

import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.base.BaseView;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.model.http.exception.ApiException;
import com.example.huangzumou.myapplication.util.LogUtil;
import com.example.huangzumou.myapplication.util.TokenUtil;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by codeest on 2017/2/23.
 */


public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mView;
    private String mErrorMsg;

    protected CommonSubscriber(BaseView view){
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    @Override
    public void onComplete() {

        RealmUserBean realmUserBean = App.getAppComponent().getContext().getUser();

        if(realmUserBean != null ){
            //LogUtil.i(String.valueOf(realmUserBean.getExpireTime()));
            if(TokenUtil.checkTokenExpire(realmUserBean)){
                //LogUtil.i("Token 已过期");
                mView.returnToLogin();
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null)
            return;
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showError(mErrorMsg);
        } else if (e instanceof ApiException) {
            mView.showError(e.toString());
        } else if (e instanceof HttpException) {
            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mView.showError("未知错误ヽ(≧Д≦)ノ");
            LogUtil.d(e.toString());
        }
    }
}
