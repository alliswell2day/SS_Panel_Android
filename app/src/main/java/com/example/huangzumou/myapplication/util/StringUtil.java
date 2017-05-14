package com.example.huangzumou.myapplication.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by huangzumou on 2017/4/28.
 */

public class StringUtil {

    public static String getDoubleString(String str){

        if(str != null && !str.isEmpty()){
            //LogUtil.i(str);
            Matcher matcher = Pattern.compile("^\\d+\\.\\d+|\\d+$").matcher(str);
            if(matcher.find()){
                //LogUtil.i(matcher.group(0));
                return matcher.group(0);
            }else {
                return "n/a";
            }
        }

        return str;

    }
}
