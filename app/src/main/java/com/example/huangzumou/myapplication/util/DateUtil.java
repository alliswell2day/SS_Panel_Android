package com.example.huangzumou.myapplication.util;

import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by huangzumou on 2017/4/30.
 */

public class DateUtil {


    public final static String FORMAT_1 =  "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_2 =  "yyyy-MM-dd";

    /**
     * 时间格式转换 将 String 格式 ，转换为 Date
     *
     */

    public static Date String2Date(String formatStr,String str){

        DateFormat format = new SimpleDateFormat(formatStr);

        if(str != null && !str.isEmpty()){
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 时间格式转换 将Date 转换为 String
     *
     */

    public static String Date2String(String formatStr,Date date){

        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        if(date != null){
            return format.format(date);
        }
        return null;

    }


    /**
     * 时间计算
     */

    public static Date dateCal(Date date,int days){

        if(date != null && days != 0){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,days);
            return calendar.getTime();
        }

        return null;

    }

    /**
     *
     * 获取当前long 型时间
     * @return
     */
    public static long getNow(){
        return (long)new Date().getTime()/1000;
    }


}
