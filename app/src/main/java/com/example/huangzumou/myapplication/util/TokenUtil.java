package com.example.huangzumou.myapplication.util;

import com.example.huangzumou.myapplication.app.Constants;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;

import java.util.Date;

/**
 * Created by huangzumou on 2017/5/1.
 */

public class TokenUtil {

    public static boolean checkTokenExpire(RealmUserBean realmUserBean){

        if(realmUserBean != null){

            long expire_time = realmUserBean.getExpireTime();
            //LogUtil.i("当前时间"+String.valueOf(DateUtil.getNow()));
            //LogUtil.i("时间差"+String.valueOf(expire_time - DateUtil.getNow()));
            if(expire_time - DateUtil.getNow() < Constants.TOKEN_EXPIRE_TIME){
                return true;
            }else {
                return false;
            }

        }else {
            return true;
        }

    }

}
