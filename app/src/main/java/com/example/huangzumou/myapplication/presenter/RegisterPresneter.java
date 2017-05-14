package com.example.huangzumou.myapplication.presenter;


import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.model.bean.TokenBean;
import com.example.huangzumou.myapplication.model.bean.TokenInfoBean;
import com.example.huangzumou.myapplication.model.db.RealmHelper;
import com.example.huangzumou.myapplication.model.http.RetrofitHelper;
import com.example.huangzumou.myapplication.model.http.response.SsPanelResponse;
import com.example.huangzumou.myapplication.presenter.contract.RegisterContract;
import com.example.huangzumou.myapplication.base.RxPresenter;
import com.example.huangzumou.myapplication.util.RxUtil;
import com.example.huangzumou.myapplication.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by huangzumou on 2017/4/22.
 */

public class RegisterPresneter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    @Inject
    public RegisterPresneter(RetrofitHelper mRetrofitHelper,RealmHelper mRealHelper){
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealHelper;
    }



    @Override
    public void register(String name, String email, String passwd, String repasswd) {
        addSubscribe(mRetrofitHelper.register(name,email, passwd,repasswd)
                .compose(RxUtil.<SsPanelResponse>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<SsPanelResponse>(mView){
                    @Override
                    public void onNext(SsPanelResponse ssPanelResponse) {
                        //LogUtil.i(tokenBean.getToken());
                        mView.showResult(ssPanelResponse.getRet(),ssPanelResponse.getMsg());
                        mView.returnToLogin();
                    }
                })
        );
    }
}
