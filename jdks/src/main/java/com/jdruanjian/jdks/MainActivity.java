package com.jdruanjian.jdks;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jdruanjian.jdks.ui.fragment.Fragment1;
import com.jdruanjian.jdks.ui.fragment.Fragment2;
import com.jdruanjian.jdks.ui.fragment.Fragment3;
import com.jdruanjian.jdks.view.BottomBar;


public class MainActivity extends FragmentActivity implements
        BottomBar.OnItemChangedListener {
    public static final String TAB = "tab";
    public static final String TAB_1 = "tab_1";
    public static final String TAB_2 = "tab_2";
    public static final String TAB_3 = "tab_3";
    public static final String TAB_4 = "tab_4";
    public static final String TAB_5 = "tab_5";
    private FragmentTransaction transaction;
    private boolean appInBackground = false;
    private HomeKeyClickReceiver homeKeyClickReceiver = null;
    public BottomBar bottomBar;
    public Fragment mContent;
    public Activity mActivity;
    public Fragment1 mFragment1;
    public Fragment2 mFragment2;
    public Fragment3 mFragment3;

    private boolean mFinish = false;
    private int tab;
    private boolean moveComplete;
    private float x, y;
    private boolean isFirst = true;
    public static boolean isForeground = false;
    private Dialog dialog;
    private GoogleApiClient client;

    public static void startActivity(Activity activity, int tab) {
       /* Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(TAB, tab);
        activity.startActivity(intent);*/
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            resetFinishToast();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  if ( VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }*/
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }

        setContentView(R.layout.activity_main);
        tab = getIntent().getIntExtra(TAB, 0);
        bottomBar = (BottomBar) findViewById(R.id.ll_bottom_bar);
        transaction = getSupportFragmentManager().beginTransaction();

        mFragment1 = new Fragment1();
        mFragment2 = new Fragment2();
        mFragment3 = new Fragment3();

        switch (tab) {
            case 0:
                mContent = mFragment1;
                break;
            case 1:
                mContent = mFragment2;
                break;
            case 2:
                mContent = mFragment3;
                break;

        }
        transaction.replace(R.id.fragments,mContent,TAB).commit();

        bottomBar.setOnItemChangedListener(this);
        bottomBar.setSelectedState(tab);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        homeKeyClickReceiver = new HomeKeyClickReceiver();
//        registerReceiver(homeKeyClickReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Override
    protected void onResume() {
        isForeground = false;
        super.onResume();

        if (!isFirst) {
            switch (tab) {
                case 0:
                    //             mFragment1.;
                    break;
                case 1:
//                mFragment2.refresh();
                    break;
                case 3:
                    break;
                case 4:
//                    mFragment5.refresh();
                    break;
                default:
                    break;
            }
        }
        isFirst = false;

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public void onItemChanged(int index) {
        showDetails(index);

    }

    public void showDetails(int index) {
        switch (index) {
            case 0:
                if (tab != 0) {
                    mFragment1.onResume();
                }
                tab = 0;
                switchContent(mContent, mFragment1);
                break;
            case 1:
                if (tab != 1) {
                    mFragment1.onHiddenChanged(true);
                }
                tab = 1;
                switchContent(mContent, mFragment2);
                break;
            case 2:
                if (tab != 2) {
                    mFragment3.onResume();
                }
                tab = 2;
                switchContent(mContent, mFragment3);
                break;

        }
    }

    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fragments, to,TAB).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
            mContent = to;
        }
    }

   /* private void setCostomMsg(String msg){
        if (null != msgText) {
            msgText.setText(msg);
            msgText.setVisibility(android.view.View.VISIBLE);
        }
    }*/

    /**
     * 退出应用，三秒内两次返回键
     */
    private void confirmFinish() {
        if (mFinish) {
            finish();
        } else {
          //  toastIfActive(R.string.exit_by_back_again);
            mFinish = true;
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    @Override
    public void onBackPressed() {
        confirmFinish();
    }

    private class HomeKeyClickReceiver extends BroadcastReceiver {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                /**
                 * 这里注意：只需要检测App在前台时的Home键点击动作，如果在后台，就不做任何动作
                 *      因此这里需要添加appInBackground参数保证app当时不在后台
                 */
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY) && !appInBackground) {
       //             Log.d(TAG, "home key clicked!");
     //               Toast.makeText(MainActivity.this, "主页键点击", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
  //             Log.d(TAG, "menu key clicked!");
                Toast.makeText(MainActivity.this, "菜单键点击", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case KeyEvent.KEYCODE_BACK:
//                Log.d(TAG, "back key clicked!");
      //          Toast.makeText(MainActivity.this, "返回键点击", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 重置第一次退出事件
     */
    private void resetFinishToast() {
        mFinish = false;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        appInBackground = false;
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        appInBackground = true;
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

    }



}
