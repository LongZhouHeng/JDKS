package com.jdruanjian.jdks.model.entity;

import java.io.Serializable;

/**
 * Created by 龙龙 on 2017/9/6.
 */

public class NewKsLotBean implements Serializable{


    /**
     * lottery_full_name : 江苏快三
     * lottery_name : type_jsks
     * number : 336
     * period : 20170919044
     * time_current : 20170919155000
     */

    private String lottery_full_name;
    private String lottery_name;
    private String number;
    private String period;
    private String time_current;

    public String getLottery_full_name() {
        return lottery_full_name;
    }

    public void setLottery_full_name(String lottery_full_name) {
        this.lottery_full_name = lottery_full_name;
    }

    public String getLottery_name() {
        return lottery_name;
    }

    public void setLottery_name(String lottery_name) {
        this.lottery_name = lottery_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime_current() {
        return time_current;
    }

    public void setTime_current(String time_current) {
        this.time_current = time_current;
    }
}
