package com.jdruanjian.jdks.model.basicmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Longlong on 2018/1/15.
 */

public class BannerModel implements Serializable{


    /**
     * status : 0
     * data : [{"url":"https://jindi163.com","img":"https://jindi163.com:8443/lot/user/20180101/jincaizhushou.png"}]
     */

    public String status;
    public List<DataBean> data;
    public String msg;
    public static class DataBean {
        /**
         * url : https://jindi163.com
         * img : https://jindi163.com:8443/lot/user/20180101/jincaizhushou.png
         */

        public String url;
        public String img;
    }
}
