package com.jdruanjian.jdks.model.entity;

import java.io.Serializable;

/**
 * Created by Longlong on 2017/12/29.
 */

public class LoginBean implements Serializable{


    /**
     * status : 0
     * data : {"uname":"duan","uid":"2","qq":"978577807","createTime":"2017-12-21 16:10:16","phone":"","role":"2","effectiveTime":"2017-12-29 14:17:43"}
     */

    public String status;
    public DataBean data;
    public  String msg;

    public static class DataBean {
        /**
         * uname : duan
         * uid : 2
         * qq : 978577807
         * createTime : 2017-12-21 16:10:16
         * phone :
         * role : 2
         * effectiveTime : 2017-12-29 14:17:43
         */

        public String uname;
        public String uid;
        public String qq;
        public String createTime;
        public String phone;
        public String role;
        public String effectiveTime;
    }
}
