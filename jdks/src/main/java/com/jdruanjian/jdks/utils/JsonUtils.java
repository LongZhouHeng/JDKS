package com.jdruanjian.jdks.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json工具类
 */
public class JsonUtils {
    /**
     * get the keyValue safely
     * 
     * @param obj
     *            JSONObject not check,it must be not null,or throw
     *            NullpointException
     * @param key
     * @return if the key is null or not exist,return null. else return the
     *         value
     * 
     * */
    public static JSONArray getJsonArray(JSONObject obj, String key)
            throws JSONException {
        if (obj.isNull(key)) {
            return null;
        } else {
            return obj.getJSONArray(key);
        }
    }

    /**
     * get the keyValue safely
     * 
     * @param obj
     *            JSONObject not check,it must be not null,or throw
     *            NullpointException
     * @param key
     * @return if the key is null or not exist,return null. else return the
     *         value
     * 
     * */
    public static String getJsonString(JSONObject obj, String key)
            throws JSONException {
        if (obj.isNull(key)) {
            return null;
        } else {
            return obj.getString(key);
        }
    }

    /**
     * get the keyValue safely
     * 
     * @param obj
     *            JSONObject not check,it must be not null,or throw
     *            NullpointException
     * @param key
     * @return if the key is null or not exist,return null. else return the
     *         value
     * 
     * */
    public static JSONObject getJsonObj(JSONObject obj, String key)
            throws JSONException {
        if (obj.isNull(key)) {
            return null;
        } else {
            return obj.getJSONObject(key);
        }
    }

    /**
     * get the keyValue safely
     * 
     * @param obj
     *            JSONObject not check,it must be not null,or throw
     *            NullpointException
     * @param key
     * @return if the key is null or not exist,return null. else return the
     *         value
     * 
     * */
    public static int getInt(JSONObject obj, String key, int defValue)
            throws JSONException {
        if (obj.isNull(key)) {
            return defValue;
        } else {
            return obj.getInt(key);
        }
    }

    /**
     * get the keyValue safely
     * 
     * @param obj
     *            JSONObject not check,it must be not null,or throw
     *            NullpointException
     * @param key
     * @return if the key is null or not exist,return defValue. else return the
     *         value
     * 
     * */
    public static double getDouble(JSONObject obj, String key, double defValue)
            throws JSONException {
        if (obj.isNull(key)) {
            return defValue;
        } else {
            return obj.getDouble(key);
        }
    }

}
