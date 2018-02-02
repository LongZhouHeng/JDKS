package com.jdruanjian.jdks.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.jdks.BaseApplication;
import com.jdruanjian.jdks.Constants;
import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.model.basicmodel.NewKsLotModel;
import com.jdruanjian.jdks.model.net.BasicResponse.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class NewKsOpenAPI extends BasicRequest {


    private final RequestListener mListener;
    private NewKsLotModel model;
    private int PageNum;
    private String PageSize;
    private String table_name;
    private String uid;
    public NewKsOpenAPI(Activity activity, String table_name, int PageNum, String PageSize, RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;

        this.PageNum = PageNum;
        this.PageSize = PageSize;
        this.table_name = table_name;
    }
    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
    //    jSONObject.put("uid",uid);
        jSONObject.put("lot_type","ks");
        jSONObject.put("PageNum", PageNum);
        System.out.println("AAAAAAAAAAAA"+PageNum);
        jSONObject.put("PageSize", PageSize);
        System.out.println("CCCCCCCCCCCC"+PageSize);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.NEW_KSLOT;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), NewKsLotModel.class);
            System.out.println("BBBBBBBBBBBB"+model);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<NewKsLotModel>(model));
            } else {
                mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,"请求错误"));
            }
        }catch(Exception e){
            mListener.onComplete(new BasicResponse<String>(
                    BasicResponse.FAIL, model.msg));
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,
                BaseApplication.getInst().getString(
                        R.string.errcode_network_unavailable)));
    }

    @Override
    public void onFinish(int what) {

    }

    @Override
    public void onStart(int what) {

    }

}
