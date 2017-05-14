package com.example.huangzumou.myapplication.presenter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.model.bean.GoodsBean;
import com.example.huangzumou.myapplication.model.bean.NodeBean;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.model.db.RealmHelper;
import com.example.huangzumou.myapplication.model.http.RetrofitHelper;
import com.example.huangzumou.myapplication.model.http.response.SsPanelResponse;
import com.example.huangzumou.myapplication.presenter.contract.BuyContract;
import com.example.huangzumou.myapplication.base.RxPresenter;
import com.example.huangzumou.myapplication.util.LogUtil;
import com.example.huangzumou.myapplication.util.RxUtil;
import com.example.huangzumou.myapplication.widget.CommonSubscriber;


import java.util.List;

import javax.inject.Inject;

import c.b.BP;
import c.b.PListener;
import c.b.QListener;

/**
 * Created by huangzumou on 2017/4/29.
 */

public class BuyPresenter extends RxPresenter<BuyContract.View> implements BuyContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealHelper;

/*
    @Override
    public void initBP() {
        //LogUtil.i("初始化BP");
        //BP.init(Constants.APPID);
    }
*/
    @Inject
    public BuyPresenter(RetrofitHelper mRetrofitHelper,RealmHelper mRealHelper){
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealHelper = mRealHelper;
    }

    @Override
    public void getGoodsInfo() {
        addSubscribe(mRetrofitHelper.getGoods()
                .compose(RxUtil.<SsPanelResponse<List<GoodsBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GoodsBean>>handleSsPanelResult())
                .subscribeWith(new CommonSubscriber<List<GoodsBean>>(mView){
                    @Override
                    public void onNext(List<GoodsBean> mList) {
                        mView.updateNodeInfo(mList);
                    }
                })
        );
    }



    @Override
    public void pay(final boolean alipayOrWechatPay) {

        //LogUtil.i("检查支付宝客户端");
        if (!mView.checkPackageInstalled("com.eg.android.AlipayGphone",
                "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
            mView.showError("请安装支付宝客户端");
            return;
        }

        //LogUtil.i("获取订单...");
        mView.showDialog("正在获取订单...请稍等");

        //LogUtil.i("启动支付Activity");
        //mView.startPayActivity();

        //LogUtil.i("开始支付");

        double price = mView.getPrice();

        if(price > 0){
            BP.pay(mView.getName(),mView.getBody(),mView.getPrice(),alipayOrWechatPay,new PListener(){

                @Override
                public void orderId(String s) {
                    mView.showDialog("获取订单成功!请等待跳转到支付页面~");
                }

                @Override
                public void succeed() {
                    mView.showError("支付成功");
                    buyGoods(mView.getShopID());
                    mView.hideDialog();
                }

                @Override
                public void fail(int code, String reason) {
                    if (code == -3) {
                        mView.showError(
                                "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付");
                    } else {
                        mView.showError(reason + " 支付中断!");
                    }

                    mView.hideDialog();
                }

                @Override
                public void unknow() {
                    mView.showError("出现未知错误");
                    mView.hideDialog();
                }
            });
        }else{
            mView.showError("请重新刷新列表");
        }

    }

    @Override
    public void buyGoods(String shopid) {

        RealmUserBean realmUserBean = App.getAppComponent().getContext().getUser();

        if(realmUserBean != null){
            String token = realmUserBean.getToken();
            int id = realmUserBean.getUserId();

            addSubscribe(mRetrofitHelper.buyGoods(token,id,shopid)
                    .compose(RxUtil.<SsPanelResponse>rxSchedulerHelper())
                    .subscribeWith(new CommonSubscriber<SsPanelResponse>(mView){
                        @Override
                        public void onNext(SsPanelResponse response) {
                            mView.buySucceed(response.getMsg());
                        }
                    })
            );
        }else {
            mView.showError("数据错误，请重试");
        }

    }

    @Override
    public void query() {

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
