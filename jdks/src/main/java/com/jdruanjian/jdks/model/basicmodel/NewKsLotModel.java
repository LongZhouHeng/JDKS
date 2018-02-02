package com.jdruanjian.jdks.model.basicmodel;

import com.jdruanjian.jdks.model.entity.KSlotBean;
import com.jdruanjian.jdks.model.entity.NewKsLotBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Longlong on 2018/1/12.
 */

public class NewKsLotModel implements Serializable{


    /**
     * status : 0
     * data : {"qq":"132456789","priceWeek":"200.00","priceMonth":"300.00","priceHalfYear":"600.00","priceYear":"1000.00"}
     */

    public String status;
    public List<NewKsLotBean> datas;
    public String msg;

}
