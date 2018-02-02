/**
 * Author: S.J.H
 * 
 * Date: 2016/3/21
 */
package com.jdruanjian.jdks.model.net;


public class BasicResponse<T> {
    public static final int SUCCESS = 0;// 请求成功
    public static final int FAIL = -1;// 请求失败
    public static final int TOKEN_EXPIRED = -2;// 登录已过期
    public T model;
    public int error; // 请求状态
    public String desc;// 错误信息

    public BasicResponse(int error) {
        this.error = error;
    }

    public BasicResponse(int code, String desc) {
        this.error = code;
        this.desc = desc;
        if (this.error == TOKEN_EXPIRED) {
            // TODO 跳转到登录页面
        }
    }

    public BasicResponse(T model) {
        this.model = model;
        error = SUCCESS;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("error = " + error).append(" ");
        sb.append("desc = " + desc).append(" ");
        return sb.toString();
    }

    /**
     * 网络请求Listener
     * 
     */
    public static interface RequestListener {
        /**
         * 请求完成的回调
         *
         *
         * @param response
         */
      //    mListener.onComplete(new BasicResponse<String>(response.FAIL,"请求错误"));
        public void onComplete(BasicResponse response);


    }

}
