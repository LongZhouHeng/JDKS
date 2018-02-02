/**
 * Author: S.J.H
 *
 * Date: 2016/7/1
 */
package com.jdruanjian.jdks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hs.view.progress.ProgressView;
import com.jdruanjian.jdks.utils.LogUtil;


/**
 * Activity的基类
 */
@SuppressLint("Registered")
public class BaseActivity extends FragmentActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        pView = new ProgressView(this);
    }
   public void setBar(){
       ImageView ivTop = (ImageView) findViewById(R.id.iv_top);
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
    protected void onDestroy() {
        super.onDestroy();
     //   BaseApplication.getInst().getRequestQueue().cancelBySign(this);
    }

    @Override
    protected void onResume() {
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
        if (TextUtils.isEmpty(str)) {
            pView.setText(R.string.loading_normal);
        } else {
            pView.setText(str);
        }
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
        if (pView != null && pView.isShown()) {
            pView.dismiss();
        }
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
     *            字符串资资源
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
    protected void showLog(String TAG, String msg) {
        if (!TextUtils.isEmpty(msg))
            LogUtil.i(TAG, msg);
    }

    /**
     * 打印日志
     *
     *
     *            msg
     */
    protected void showLog(String msg) {
        if (!TextUtils.isEmpty(msg))
            LogUtil.i(msg);
    }

    /**
     * activity跳转
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

    /**
     * startActivityForResult跳转
     *
     *            <?> cls
     */
    public void startActivityForResult(Class<?> cls) {
        startActivityForResult(cls, null, 0);
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
     * 返回结果到上一个activity
     *
     *

     *            <?> cls
     */
    public void setResult(int resultCode, Class<?> cls) {
        setResult(resultCode, cls, null);
    }

    /**
     * 返回结果到上一个activity
     *
     *
     *
     *            <?> cls
     *
     *            bundle
     */
    public void setResult(int resultCode, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(resultCode, intent);
        showLog("resultCode" + resultCode);
        finish();
    }

    /** 进入该activity是否需要登录状态 */
    protected boolean needLogin() {
        return false;
    }

    /**
     * 返回键
     *
     */
    public void btn_back(View v) {
        finish();
    }
    public void btn_back_1(View v) {
        finish();
    }
    /**
     * 设置标题
     */
    public void setTitle(String str) {
      //  TextView title = (TextView) findViewById(R.id.tv_title);
  //      title.setText(str);
    }
    /**
     * 设置标题
     */
    public void setTitleResult( String str) {
   //     TextView title = (TextView) findViewById(R.id.tv_title_result);
  //      title.setText(str);
    }
    /**
     * 设置标题
     */
    public void setTitleResult1( String str) {
        TextView title = (TextView) findViewById(R.id.tv_title_result_1);
        title.setText(str);

    }
    /**
     * 设置标题
     */
    public void setTitlebar(String str) {
        TextView title = (TextView) findViewById(R.id.tv_title_bar);
        title.setText(str);

    }
    /**
     * 设置标题1
     */
    public void setTitlebar1(String str) {
        TextView title = (TextView) findViewById(R.id.tv_save_info);
        title.setText(str);
        /*TextPaint tp = title.getPaint();
        tp.setFakeBoldText(true);*/
    }
    /**
     * 设置标题1
     */
    public void setTitleotther(String str) {
    //    TextView title = (TextView) findViewById(R.id.tv_title_jieguo);
   //     title.setText(str);

    }
    /**
     * 设置标题栏按钮图片
     *
     * @param what
     *            0左1右
     */
    /*public void setTitleButton(int what, int rid,
                               android.view.View.OnClickListener listener) {
        ImageButton btn;
        if (what == 0) {
            btn = (ImageButton) findViewById(R.id.btn_left_img);
        } else {
            btn = (ImageButton) findViewById(R.id.btn_right_img);
        }
        btn.setImageResource(rid);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(listener);
    }*/

    /**
     * 设置标题栏右文字按钮
     */
   /* public void setTitleButton(String str,
                               android.view.View.OnClickListener listener) {
        Button btn = (Button) findViewById(R.id.btn_right_text);
        btn.setText(str);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(listener);
    }*/

 /*   *//**
     * 设置车次
     *//*
    public void setTrain(String str) {
        TextView train = (TextView) findViewById(R.id.tv_train);
        train.setText(str);
    }
    //设置注销按钮
    public void setLogout(View v, String str) {
        Button logout = (Button) v.findViewById(R.id.btn_out);
        logout.setText(str);
    }
    /
     *设置注销按钮
     */
    public void showBackBtn() {
        findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
    }
    public void showBackBtn1() {
        findViewById(R.id.btn_back_1).setVisibility(View.VISIBLE);
    }
}
