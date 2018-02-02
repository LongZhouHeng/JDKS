package com.jdruanjian.jdks.model.basicmodel;

import java.io.Serializable;

/**
 * Created by Longlong on 2018/1/17.
 */

public class OrderPriceModel implements Serializable{


    /**
     * status : 0
     * data : {"4url":"http://t.cn/RQJPm4Q","5url":"http://t.cn/RQJPNte","6url":"33","3url":"http://t.cn/RQJPpBo","2url":"http://t.cn/RQJPE7r","1url":"1"}
     */

    public String status;
    public DataBean data;

    public static class DataBean {
        /**
         * 4url : http://t.cn/RQJPm4Q
         * 5url : http://t.cn/RQJPNte
         * 6url : 33
         * 3url : http://t.cn/RQJPpBo
         * 2url : http://t.cn/RQJPE7r
         * 1url : 1
         */
        public String url_4;
        public String url_5;
        public String url_6;
        public String url_3;
        public String url_2;
        public String url_1;
    }
}
