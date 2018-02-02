package com.jdruanjian.jdks.model.basicmodel;

import com.jdruanjian.jdks.model.entity.NewPeriodBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Longlong on 2018/1/4.
 */

public class NewPeriodModel implements Serializable{

    public String msg;
    public String msgContext;
    public List<NewPeriodBean> datas;
}
