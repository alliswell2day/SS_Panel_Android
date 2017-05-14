package com.example.huangzumou.myapplication.model.bean;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by huangzumou on 2017/4/22.
 */

public class RealmUserBean extends RealmObject implements Serializable {

    public RealmUserBean(){}

    @PrimaryKey
    private String email;

    private int userId;
    private String token;
    private long createTime;
    private long expireTime;
    //private boolean autoLogin;
    //private long lastLogin;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
