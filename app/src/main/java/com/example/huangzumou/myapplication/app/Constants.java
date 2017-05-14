package com.example.huangzumou.myapplication.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {

    //================= URL ====================

    public static final String API_URL = "http://myvpn.5awo.com/";


    //================= KEY ====================

    public static final String APPID = "f35ec5df360266ad274dab0a352d4499";

    //public static final String BUGLY_ID = "257700f3f8";

    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "huang" + File.separator + "app";

    //================= OTHER ====================

    public static final int TOKEN_EXPIRE_TIME = 300; //单位为秒
    public static final String DB_NAME = "myRealm.realm";

    }