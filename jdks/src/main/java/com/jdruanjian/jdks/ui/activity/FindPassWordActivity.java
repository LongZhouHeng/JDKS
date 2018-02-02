package com.jdruanjian.jdks.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jdruanjian.jdks.BaseActivity;
import com.jdruanjian.jdks.Constants;
import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.model.entity.RegisterBean;
import com.jdruanjian.jdks.utils.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Longlong on 2017/12/21.
 */

@SuppressLint("Registered")
public class FindPassWordActivity extends BaseActivity {

    @BindView(R.id.et_qqnum)
    EditText etQqnum;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_ensurepwd)
    EditText etEnsurepwd;
    @BindView(R.id.et_username)
    EditText etUsername;
    private RegisterBean model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogetpwd);
        ButterKnife.bind(this);
        setBar();
        showBackBtn();

    }

    @OnClick(R.id.btn_ensure)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(etPassword.getText().toString())&&
                etPassword.getText().toString().equals(etEnsurepwd.getText().toString())){
            doRequest();
        }else {
            Toast.makeText(getApplication(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
        }

    }

    public void doRequest() {
        if (isLoading) {
            return;
        }
        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }
        //    System.out.println("QQQQQQQQ-----=====" + collegeName);
        OkGo.post(Constants.RESET_PWD)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("username", etUsername.getText().toString())
                .params("appId", "4")
                .params("pwd", etPassword.getText().toString())
                .params("qq", etQqnum.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        model = JSON.parseObject(s.getBytes(), RegisterBean.class);
                        if (model.status.equals("0")) {
                            Intent intent1 = new Intent(FindPassWordActivity.this, LoginActivity.class);
                            startActivity(intent1);
                            Toast.makeText(getApplication(), model.data, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), model.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        Toast.makeText(getApplication(), "数据有误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
