package com.jdruanjian.jdks.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.jdruanjian.jdks.MainActivity;
import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.utils.PrefUtils;

/**
 * Created by Longlong on 2018/1/5.
 */

@SuppressLint("Registered")
public class WelcomeActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    //延迟2S跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PrefUtils.getString(getApplication(),"uid",null) !=null){

                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                }else {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 1000);
    }
}
