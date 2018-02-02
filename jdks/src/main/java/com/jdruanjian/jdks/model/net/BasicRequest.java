/**
 * Author: S.J.H
 * 
 * Date: 2016/7/1
 */
package com.jdruanjian.jdks.model.net;

import android.app.Activity;

import com.hs.nohttp.Headers;
import com.hs.nohttp.RequestMethod;
import com.hs.nohttp.rest.JsonObjectRequest;
import com.hs.nohttp.rest.OnResponseListener;
import com.hs.nohttp.rest.RestRequest;
import com.hs.nohttp.tools.HeaderParser;
import com.hs.nohttp.tools.IOUtils;
import com.jdruanjian.jdks.BaseApplication;
import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.utils.LogUtil;
import com.jdruanjian.jdks.utils.Md5Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;


public abstract class BasicRequest extends RestRequest<String> implements
        OnResponseListener<String> {
    private JSONObject mJSONObject;

    public BasicRequest(Activity activity, String url) {
        super(url, getHttpMethod());
        LogUtil.i(url);
        setCancelSign(activity);
    }

    @Override
    public String getAccept() {
        return JsonObjectRequest.ACCEPT;
    }

    public static RequestMethod getHttpMethod() {
        return RequestMethod.POST;
    }

    public JSONObject getObject() throws JSONException {
        mJSONObject = new JSONObject();
       /*if (isNeedToken()) {
            addToken(BaseApplication.getInst().getDeviceId(),
                    TimeUtils.TimeToUpload(System.currentTimeMillis()));
        }*/
        return mJSONObject;
    }

    /** 是否需要登录 */
    public boolean isNeedLogin() {
        return false;
    }

    /** 是否需要验证 */
    public boolean isNeedToken() {
        return false;
    }

    /**
     * 添加验证
     * 
     * @throws JSONException
     */
    public void addToken(String mac, String time) throws JSONException {
        mJSONObject.put("mac", mac);
        mJSONObject.put("time", time);
        mJSONObject.put("token",
                Md5Util.MD5(Md5Util.MD5(mac).toUpperCase() + time)
                        .toUpperCase());
    }

    /**
     * 添加验证
     * 
     */
    public void addToken2(String mac, String time) {
        add("mac", mac);
        add("time", time);
        add("token", Md5Util.MD5(Md5Util.MD5(mac).toUpperCase() + time)
                .toUpperCase());
    }

    @Override
    public String parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        return parseResponseString(url, responseHeaders, responseBody);
    }

    public static String parseResponseString(String url, Headers responseHeaders, byte[] responseBody) {
        if (responseBody == null)
            return "";
        return IOUtils.toString(responseBody, HeaderParser.parseHeadValue(
                responseHeaders.getContentType(),
                Headers.HEAD_KEY_CONTENT_TYPE, ""));
    }

    public void setBody(BasicResponse.RequestListener listener) {
        try {
            setDefineRequestBody(new ByteArrayInputStream(getObject().toString().getBytes()), getContentType());
        } catch (JSONException e) {
            listener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,
                    BaseApplication.getInst().getString(R.string.errcode_network_data)));
            e.printStackTrace();
        }
    }

    @Override
    public String getContentType() {
        return getAccept();
    }

}
