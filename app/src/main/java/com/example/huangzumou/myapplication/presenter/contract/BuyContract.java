package com.example.huangzumou.myapplication.presenter.contract;

import com.example.huangzumou.myapplication.base.BasePresenter;
import com.example.huangzumou.myapplication.base.BaseView;
import com.example.huangzumou.myapplication.model.bean.GoodsBean;

import java.util.List;

/**
 * Created by huangzumou on 2017/4/29.
 */

public interface BuyContract {

    interface View extends BaseView {

        void updateNodeInfo(List<GoodsBean> mList);
        GoodsBean getSelectedNode();
        void showDialog(String msg);
        void hideDialog();
        String getName();
        String getBody();
        double getPrice();
        String getShopID();
        boolean checkPackageInstalled(String packageName, String browserUrl);
        void startPayActivity();
        void buySucceed(String msg);
    }

    interface Presenter extends BasePresenter<BuyContract.View> {

        //void initBP();
        void getGoodsInfo();
        void pay(final boolean alipayOrWechatPay);
        void query();
        void buyGoods(String shopid);
        void deleteUser();


    }
}
