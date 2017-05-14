package com.example.huangzumou.myapplication.model.http.api;

import com.example.huangzumou.myapplication.model.bean.GoodsBean;
import com.example.huangzumou.myapplication.model.bean.NodeBean;
import com.example.huangzumou.myapplication.model.bean.TokenInfoBean;
import com.example.huangzumou.myapplication.model.bean.UserInfoBean;
import com.example.huangzumou.myapplication.model.http.response.SsPanelResponse;
import com.example.huangzumou.myapplication.model.bean.TokenBean;
import com.example.huangzumou.myapplication.app.Constants;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;


public interface SsPanelApis {

    String HOST = Constants.API_URL;

    @FormUrlEncoded
    @POST("api/token")
    Flowable<SsPanelResponse<TokenBean>> getToken(@Field("email") String email, @Field("passwd") String passwd);


    @FormUrlEncoded
    @POST("auth/register")
    Flowable<SsPanelResponse> register(@Field("name") String name, @Field("email") String email,
                                       @Field("passwd") String passwd,@Field("repasswd") String repasswd,
                                       @Field("imtype") int imtype,@Field("wechat") String wechat);



    @GET("api/token/{token}")
    Flowable<SsPanelResponse<TokenInfoBean>> getTokenInfo(@Path("token") String token);

    @GET("api/user/{id}")
    Flowable<SsPanelResponse<UserInfoBean>> getUserInfo(@Path("id") int user_id, @Query("access_token") String token);

    @GET("api/node")
    Flowable<SsPanelResponse<List<NodeBean>>> getNodeInfo(@Query("access_token") String token);

    @GET("api/shop")
    Flowable<SsPanelResponse<List<GoodsBean>>> getGoods();

    @FormUrlEncoded
    @POST("api/buy")
    Flowable<SsPanelResponse> buyGoods(@Query("access_token") String token,@Field("id") int id, @Field("shopid") String shopid);

}
