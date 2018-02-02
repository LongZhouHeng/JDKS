/**
 * Author: S.J.H
 * 
 * Date: 2016/7/1
 */
package com.jdruanjian.jdks;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hs.view.progress.ProgressView;

/**
 * Fragment的基类
 */
public abstract class BaseFragment extends Fragment {
    protected boolean isLoading = false;
    protected Activity activity;
    /**
     * 不确定进度条
     */
    protected ProgressView pView;
    /**
     * 提示
     */
    protected Toast mToast;
    /**
     * activity状态
     */
    private boolean mIsPause;
    /**
     * 请求状态
     */
    protected boolean mIsLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        pView = new ProgressView(activity);
    }
    public void setBar(View v){
        ImageView ivTop = (ImageView) v.findViewById(R.id.iv_top);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams lp = ivTop.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = getStatusBarHeight();
            ivTop.setLayoutParams(lp);
            ivTop.setVisibility(View.VISIBLE);
        }
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    @Override
    public void onStop() {
        super.onStop();
        mIsPause = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  BaseApplication.getInst().getRequestQueue().cancelBySign(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsPause = false;
    }

    /**
     * 显示不确定进度条
     * 
     *
     *            str
     */
    public void showProgressView(String str) {
        this.mIsLoading = true;
        if (TextUtils.isEmpty(str))
            pView.setText(R.string.loading_normal);
        else
            pView.setText(str);
        pView.show();
    }

    /**
     * 显示不确定进度条
     * 
     *
     */
    public void showProgressView(int resId) {
        this.mIsLoading = true;
        if (resId == 0)
            pView.setText(R.string.loading_normal);
        else
            pView.setText(resId);
        pView.show();
    }

    /**
     * 显示不确定进度条
     */
    public void showProgressView() {
        this.mIsLoading = true;
        showProgressView(0);
    }

    /**
     * 隐藏不确定进度条
     */
    public void dismissProgressView() {
        mIsLoading = false;
        pView.dismiss();
    }

    /**
     * 显示 toast 提示
     * 
     * @param text
     *            提示语句
     * */
    public void toastIfActive(String text) {
        if (!this.mIsPause) {
            if (mToast == null) {
                mToast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    /**
     * 显示 toast 提示
     * 
     * @param resId
     *            字符串资 *
     */
    public void toastIfActive(int resId) {
        if (!this.mIsPause) {
            if (mToast == null) {
                mToast = Toast.makeText(activity, resId, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 打印日志
     * 
     *
     *            TAG
     *
     *            msg
     */
   /* protected void showLog(String TAG, String msg) {
        if (!TextUtils.isEmpty(msg))
         //   LogUtil.i(TAG, msg);
    }*/

    /**
     * 打印日志
     * 
     *
     *            msg
     */
   /* protected void showLog(String msg) {
        if (!TextUtils.isEmpty(msg))
            LogUtil.i(msg);
    }*/

    /**
     * activity跳转
     * 
     *
     *            <?> cls
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * activity跳转
     * 
     *
     *            <?> cls
     *
     *            bundle
     */

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void finishActivity() {
        activity.finish();
    }

    /**
     * startActivityForResult跳转
     * 
     *
     *            <?> cls
     *
     *            bundle
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle) {
        startActivityForResult(cls, bundle, 0);
    }

    /**
     * startActivityForResult跳转
     * 
     *
     *            <?> cls
     *
     *            bundle
     *
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 设置标题
     */
    public void setTitle(View v, String str) {
      //  TextView title = (TextView) v.findViewById(R.id.tv_title);
   //     title.setText(str);
    }

    /**
     * 设置标题
     */
    public void setTitlebar(View v, String str) {
        TextView title = (TextView) v.findViewById(R.id.tv_title_bar);
        title.setText(str);

    }
    /**
     * 设置标题
     */
    public void setTitleResult1(View v , String str) {
        TextView title = (TextView) v.findViewById(R.id.tv_title_result_1);
        title.setText(str);
        TextPaint tp = title.getPaint();
        tp.setFakeBoldText(true);
    }
    /**
     * 返回键
     *
     */

  /*  *//**
     * 设置车次
     *//*
    public void setTrain(View v, String str) {
        TextView train = (TextView) v.findViewById(R.id.tv_train);
        train.setText(str);
    }
*/

}
