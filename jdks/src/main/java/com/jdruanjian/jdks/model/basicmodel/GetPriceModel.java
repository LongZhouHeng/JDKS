package com.jdruanjian.jdks.model.basicmodel;

import java.io.Serializable;

/**
 * Created by Longlong on 2018/1/12.
 */

public class GetPriceModel implements Serializable{


    /**
     * status : 0
     * data : {"qq":"132456789","priceWeek":"200.00","priceMonth":"300.00","priceHalfYear":"600.00","priceYear":"1000.00"}
     */

    public String status;
    public DataBean data;
    public String msg;
    public static class DataBean {
        /**
         * qq : 132456789
         * priceWeek : 200.00
         * priceMonth : 300.00
         * priceHalfYear : 600.00
         * priceYear : 1000.00
         */

        public String qq;
        public String priceWeek;
        public String priceMonth;
        public String priceHalfYear;
        public String priceYear;
        public String priceForever;
    }
}
