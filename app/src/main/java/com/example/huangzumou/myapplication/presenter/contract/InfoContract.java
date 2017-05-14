package com.example.huangzumou.myapplication.presenter.contract;

import com.example.huangzumou.myapplication.base.BasePresenter;
import com.example.huangzumou.myapplication.base.BaseView;
import com.example.huangzumou.myapplication.model.bean.NodeBean;
import com.example.huangzumou.myapplication.model.bean.UserInfoBean;
import com.example.huangzumou.myapplication.socks.core.LocalVpnService;

import java.util.List;


/**
 * Created by huangzumou on 2017/4/22.
 */

public interface InfoContract {

    interface View extends BaseView {

        void updateUserInfo(UserInfoBean userInfoBean);
        void updateNodeInfo(List<NodeBean> mList);
        void setConnectButtonState(boolean isRunning);
        void connect();
        void disconnect();
        void jumpToLogin();
        void buyGoods();
        NodeBean getSelectedNode();
        //void showLog(String log);

        //NodeBean getSelectedNode();


    }

    interface Presenter extends BasePresenter<InfoContract.View> {

        void getUserInfo();
        void getNodeInfo();
        //void logout();
        void deleteUser();


    }
}
