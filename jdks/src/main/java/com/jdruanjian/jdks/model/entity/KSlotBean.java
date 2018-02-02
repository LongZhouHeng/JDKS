package com.jdruanjian.jdks.model.entity;

import java.io.Serializable;

/**
 * Created by 龙龙 on 2017/9/7.
 */

public class KSlotBean implements Serializable{


    /**
     * number : 346
     * period : 20170830045
     * time_current : 20170830133000
     * time_next : 20170830134000
     */

    private String number;
    private String period;
    private String time_current;
    private String time_next;

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

    public String getTime_next() {
        return time_next;
    }

    public void setTime_next(String time_next) {
        this.time_next = time_next;
    }
}
