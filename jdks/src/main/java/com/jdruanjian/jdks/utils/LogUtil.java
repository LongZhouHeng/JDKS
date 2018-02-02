package com.jdruanjian.jdks.utils;

import android.util.Log;

import com.jdruanjian.jdks.Constants;


/**
 * Log工具类
 */
public class LogUtil {
    public static final String DEFAULT_TAG = Constants.TAG;
    public static boolean showLog = Constants.DEVELOPER_MODE;

    public static void v(String logText) {
        if (showLog) {
            Log.v(DEFAULT_TAG, logText);
        }
    }

    public static void v(String TAG, String logText) {
        if (showLog) {
            Log.v(TAG, logText);
        }
    }

    public static void i(String logText) {
        if (showLog) {
            Log.i(DEFAULT_TAG, logText);
        }
    }

    public static void i(String TAG, String logText) {
        if (showLog) {
            Log.i(TAG, logText);
        }
    }

    public static void w(String TAG, String logText) {
        if (showLog) {
            Log.w(TAG, logText);
        }
    }

    public static void w(String logText) {
        if (showLog) {
            Log.w(DEFAULT_TAG, logText);
        }
    }

    public static void e(String logText) {
        if (showLog) {
            Log.e(DEFAULT_TAG, logText);
        }
    }

    public static void e(String TAG, String logText) {
        if (showLog) {
            Log.e(TAG, logText);
        }
    }

    public static void d(String logText) {
        if (showLog) {
            Log.d(DEFAULT_TAG, logText);
        }
    }

    public static void d(String TAG, String logText) {
        if (showLog) {
            Log.d(TAG, logText);
        }
    }

    public static void d(Class<?> c, String logText) {
        if (showLog) {
            Log.d(c.getSimpleName(), logText);
        }
    }

    public static void d(Object c, String logText) {
        if (showLog) {
            Log.d(c.getClass().getSimpleName(), logText);
        }
    }
}
