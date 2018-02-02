package com.jdruanjian.jdks.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/11/16.
 */

public class BaseUtils {
    public static String token(String phone, String id){
        String APPKEY = toMD5("YouQin");
       // String TIME = sf.format(new Date());
        String TIME = getNewTime();
        String TOKEN = toMD5(TIME+id+phone+APPKEY)+"|"+TIME;
        Log.e("TOKEN", TOKEN);
        return TOKEN;
    }


    public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH");

    public static String getNewTime(){

        //long time=System.currentimeMillis()/1000;//获取系统时间的10位的时间戳

        Long time = System.currentTimeMillis()/1000;
        String str= String.valueOf(time);

        return str;

    }


//    /* 时间戳转换成字符串 */
//    public static String getDateToString(String dateTime, SimpleDateFormat format) {
//        long time = Long.parseLong(dateTime);
//        Date date = new Date(time);
//        return format.format(date);
//    }

    public static String toMD5(String plainText) {
        StringBuffer buf = null;
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
             buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();// 32位的加密
    }
    public static String getTime(){
    	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
    	String date = sDateFormat.format(new Date());
		return date;
    }

}
