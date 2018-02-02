package com.jdruanjian.jdks.utils;

import java.text.DecimalFormat;

public class StringUtil {
    public static String toPrice(String price) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            return "￥ " + df.format(Float.valueOf(price) / 100);
        } catch (Exception e) {
            return "￥ 0.00";
        }
    }

    public static String toPrice2(float price) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(price) + "元";
        } catch (Exception e) {
            return "0.00" + "元";
        }
    }

    public static String toPrice3(String price) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(Float.valueOf(price) / 100);
        } catch (Exception e) {
            return " 0.00";
        }
    }

    public static String toPrice4(String price) {
        try {
            return "￥ " + (Integer.valueOf(price) / 100);
        } catch (Exception e) {
            return "￥ 0";
        }
    }
}
