package com.jdruanjian.jdks.model.basicmodel;

import com.jdruanjian.jdks.model.entity.ResultListBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Longlong on 2017/12/7.
 */

public class QueryResultModel implements Serializable{


    /**
     * status : 0
     * data : [{"collegeName":"安徽大学","majorName":"金融学","suggest":"弃","fractionalLine17":"627","duan":"1","nameNumber17":"12757","majorId":"7","collegeId":"3404","infoId":"1036"}]
     */

    public String status;
    public List<ResultListBean> data;


}
