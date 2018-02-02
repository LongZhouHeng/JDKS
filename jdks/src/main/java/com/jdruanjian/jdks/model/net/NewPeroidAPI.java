package com.jdruanjian.jdks.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.jdks.BaseApplication;
import com.jdruanjian.jdks.Constants;
import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.model.basicmodel.NewPeriodModel;

import org.json.JSONException;
import org.json.JSONObject;

public class NewPeroidAPI extends BasicRequest {

  //  private final static String INTERFACE = "http://47.94.140.92:8080/JDLot/type/new/number";
    private final BasicResponse.RequestListener mListener;
    private NewPeriodModel model;
    private String lottery_name;
    public NewPeroidAPI(Activity activity, String lottery_name, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
    //    Toast.makeText(activity, getHttpUrl(),Toast.LENGTH_LONG).show();
        this.mListener = mListener;
        this.lottery_name = lottery_name;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("lottery_name", lottery_name);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.NEW_NUMBER;

    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), NewPeriodModel.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<NewPeriodModel>(model));
            } else {
                mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,"请求错误"));
            }
        }catch(Exception e){
            mListener.onComplete(new BasicResponse<String>(
                    BasicResponse.FAIL,""));
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,
                BaseApplication.getInst().getString(
                        R.string.errcode_network_unavailable)));
    }
    @Override
    public void onStart(int what) {

    }



    @Override
    public void onFinish(int what) {

    }

}
