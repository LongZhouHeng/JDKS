package com.jdruanjian.jdks.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jdruanjian.jdks.BaseFragment;
import com.jdruanjian.jdks.Constants;
import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.adapter.Random1ResultAdapter;
import com.jdruanjian.jdks.model.basicmodel.BannerModel;
import com.jdruanjian.jdks.model.basicmodel.PlanRankModel;
import com.jdruanjian.jdks.model.basicmodel.UpDateVerSion;
import com.jdruanjian.jdks.model.entity.PlanRankBean;
import com.jdruanjian.jdks.utils.GlideImageLoader;
import com.jdruanjian.jdks.utils.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Longlong on 2017/12/19.
 */

public class Fragment1 extends BaseFragment {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    Unbinder unbinder;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.banner)
    Banner banner;
    private PlanRankModel model;
    //实例化的数组或者集合
    private ArrayList<PlanRankBean> resulrlist;
    private Random1ResultAdapter mAdapter;
    private int pageNum = 1;
    private BannerModel bannerModel;
    List<String> bannerlist = new ArrayList<>();
    private UpDateVerSion upDateVerSion;
    private String version;
    private Dialog dialog;
    private ProgressDialog downloadProDialog;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment_1, container, false);
        unbinder = ButterKnife.bind(this, layout);
        setBar(layout);
        setTitlebar(layout, "排行榜");
        resulrlist = new ArrayList<PlanRankBean>();
        mAdapter = new Random1ResultAdapter(getActivity(), resulrlist);
        lv.setAdapter(mAdapter);
        doRequest();
        getBanner();
        VersionUpdate();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (bannerModel.data.get(position).url != null) {
                    Uri uri = Uri.parse(bannerModel.data.get(position).url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplication(), "还未开放链接", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void doRequest() {
        if (isLoading) {
            return;
        }
        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }
        //    System.out.println("QQQQQQQQ-----=====" + collegeName);
        OkGo.post(Constants.QUERY_RANK)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appId", "4")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        model = JSON.parseObject(s.getBytes(), PlanRankModel.class);
                        if (model.status.equals("0")) {
                            resulrlist.clear();
                            resulrlist.addAll((ArrayList<PlanRankBean>) model.data);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity().getApplication(), "数据有误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        Toast.makeText(getActivity().getApplication(), "数据有误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getBanner() {
        if (isLoading) {
            return;
        }
        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }
        OkGo.post(Constants.BANNER_PAGE)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appId", "4")
                .params("os", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        bannerModel = JSON.parseObject(s.getBytes(), BannerModel.class);
                        if (bannerModel.status.equals("0")) {
                            for (int i = 0; i < bannerModel.data.size(); i++) {

                                bannerlist.add(bannerModel.data.get(i).img);


                            }
                            //设置轮播时间
                            //设置内置样式，共有六种可以点入方法内逐一体验使用。
                            //      banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                            banner.setDelayTime(3000);
                            //调用ImageApp()方法实现图片的加载
                            banner.setImageLoader(new GlideImageLoader());
                            banner.setScrollX(WindowManager.LayoutParams.MATCH_PARENT);
                            banner.setScrollY(WindowManager.LayoutParams.MATCH_PARENT);
                            banner.setImages(bannerlist);
                            banner.start();
                        } else {
                            Toast.makeText(getActivity().getApplication(), bannerModel.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        Toast.makeText(getActivity().getApplication(), "数据有误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void VersionUpdate() {
        if (isLoading) {
            return;
        }
        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }
        OkGo.post(Constants.CHECK_VERSION)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appId", "4")
                .params("os", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        upDateVerSion = JSON.parseObject(s.getBytes(), UpDateVerSion.class);
                        if (upDateVerSion.status.equals("0")) {
                            version = upDateVerSion.data;
                            System.out.println("111111+++++===" + version);
                            checkVersion();
                        } else {
                            Toast.makeText(getActivity().getApplication(), bannerModel.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        Toast.makeText(getActivity().getApplication(), "数据有误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //检测本程序的版本，这里假设从服务器中获取到最新的版本号为3
    public void checkVersion() {
        //如果检测本程序的版本号小于服务器的版本号，那么提示用户更新
        if (getVersionName() < Float.parseFloat(version)) {
            showdialogupdate();
        } else {
            //否则吐司，说现在是最新的版本
            //         Toast.makeText(getActivity(),"当前已经是最新的版本",Toast.LENGTH_SHORT).show();

        }
    }

    private void showdialogupdate() {

        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_checkupdate, null);
        Button tv_ensure = (Button) localView.findViewById(R.id.btn_update);
        ImageView tv_cancel = (ImageView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(getActivity(), R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                loadNewVersionProgress();
            }
        });

    }

    /**
     * 下载新版本程序
     */
    private void loadNewVersionProgress() {
        final String uri = "http://47.104.108.204:8080/pk10/android/KS.APK";
       /* final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载...");
        pd.show();*/
        downloadProDialog = new ProgressDialog(getActivity());
        downloadProDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downloadProDialog.setTitle("正在下载");
        downloadProDialog.setMessage("下载中,请等待...");
        downloadProDialog.setMax(100);
        downloadProDialog.setProgress(0);
        downloadProDialog.setSecondaryProgress(0);
        downloadProDialog.setIndeterminate(false);
        downloadProDialog.setCanceledOnTouchOutside(false);
        downloadProDialog.setInverseBackgroundForced(false);
        downloadProDialog.show();
        //启动子线程下载任务
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(uri, downloadProDialog);
                    sleep(3000);
                    installApk(file);
                    downloadProDialog.dismiss(); //结束掉进度条对话框
                    Toast.makeText(getActivity(), "更新完成", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    //下载apk失败
                    Toast.makeText(getActivity(), "下载新版本失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // finishActivity();
        Process.killProcess(Process.myPid());
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    /*
    * 获取当前程序的版本名
    */
    private float getVersionName() {
        try {

            //获取packagemanager的实例
            PackageManager packageManager = getActivity().getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
            Log.e("TAG", "版本号" + packInfo.versionCode);
            Log.e("TAG", "版本名" + packInfo.versionName);
            return Float.parseFloat(packInfo.versionName);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return 1;


    }


    /*
     * 获取当前程序的版本号
     */
    private int getVersionCode() {
        try {

            //获取packagemanager的实例
            PackageManager packageManager = getActivity().getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
            Log.e("TAG", "版本号" + packInfo.versionCode);
            Log.e("TAG", "版本名" + packInfo.versionName);
            return packInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return 2;
    }
}
