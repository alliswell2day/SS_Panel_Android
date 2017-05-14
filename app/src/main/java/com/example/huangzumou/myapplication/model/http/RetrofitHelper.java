package com.example.huangzumou.myapplication.model.http;


import com.example.huangzumou.myapplication.model.bean.GoodsBean;
import com.example.huangzumou.myapplication.model.bean.NodeBean;
import com.example.huangzumou.myapplication.model.bean.TokenBean;
import com.example.huangzumou.myapplication.model.bean.TokenInfoBean;
import com.example.huangzumou.myapplication.model.bean.UserInfoBean;
import com.example.huangzumou.myapplication.model.http.api.SsPanelApis;
import com.example.huangzumou.myapplication.model.http.response.SsPanelResponse;


import java.util.List;
import java.util.Date;
import io.reactivex.Flowable;

/**
 * Created by codeest on 2016/8/3.
 */
public class RetrofitHelper {


    private SsPanelApis mSsPanelApiService;

    public RetrofitHelper(SsPanelApis ssPanelApiService) {
        this.mSsPanelApiService = ssPanelApiService;
    }


    public Flowable<SsPanelResponse<TokenBean>> getToken(String email,String passwd) {
        return mSsPanelApiService.getToken(email,passwd);
    }


    public Flowable<SsPanelResponse> register(String name,String email,String passwd,String repasswd) {
        int imtype = 1;
        String wechat = String.valueOf(new Date().getTime());
        return mSsPanelApiService.register(name,email,passwd,repasswd,imtype,wechat);
    }

    public Flowable<SsPanelResponse<TokenInfoBean>> getTokenInfo(String token) {
        return mSsPanelApiService.getTokenInfo(token);
    }

    public Flowable<SsPanelResponse<UserInfoBean>> getUserInfo(int user_id,String token) {
        return mSsPanelApiService.getUserInfo(user_id,token);
    }

    public Flowable<SsPanelResponse<List<NodeBean>>> getNodeInfo(String token) {
        return mSsPanelApiService.getNodeInfo(token);
    }

    public Flowable<SsPanelResponse<List<GoodsBean>>> getGoods() {
        return mSsPanelApiService.getGoods();
    }

    public Flowable<SsPanelResponse> buyGoods(String token,int id,String shopid) {
        return mSsPanelApiService.buyGoods(token,id,shopid);
    }

}
