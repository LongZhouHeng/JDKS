package com.jdruanjian.jdks.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.jdks.BaseActivity;
import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.adapter.KSListAdapter;
import com.jdruanjian.jdks.model.basicmodel.KSLotModel;
import com.jdruanjian.jdks.model.entity.KSlotBean;
import com.jdruanjian.jdks.model.net.AlreadyKSAPI;
import com.jdruanjian.jdks.model.net.BasicResponse;
import com.jdruanjian.jdks.model.net.BasicResponse.RequestListener;
import com.jdruanjian.jdks.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/8/25.
 */

public class Result_KS_Activity extends BaseActivity implements OnClickListener {
    View main;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.btn_ks)
    RelativeLayout btnKs;
    @BindView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @BindView(R.id.tv_jsks)
    TextView tvJsks;
    @BindView(R.id.tv_jlks)
    TextView tvJlks;
    @BindView(R.id.tv_ahks)
    TextView tvAhks;
    @BindView(R.id.tv_gxks)
    TextView tvGxks;
    private KSListAdapter ksListAdapter;
    private ArrayList<KSlotBean> kSlotBeenlist;
    private KSLotModel model;
    ListView mListView;
    MaterialRefreshLayout materialRefreshLayout;
    private int PageNum = 1;
    private String table_name = "jsks";
    private boolean visibility_Flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = getLayoutInflater().inflate(R.layout.activity_result_ks, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        main.setOnClickListener(this);
        setContentView(main);
        ButterKnife.bind(this);
        setBar();
        showBackBtn();
        tvJsks.setEnabled(false);
        tvJlks.setEnabled(true);
        tvAhks.setEnabled(true);
        tvGxks.setEnabled(true);
        tvTitleBar.setText("江苏快三");
        mListView = (ListView) findViewById(R.id.lv);
        kSlotBeenlist = new ArrayList<KSlotBean>();
        ksListAdapter = new KSListAdapter(this, kSlotBeenlist);
        mListView.setAdapter(ksListAdapter);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.finishRefreshLoadMore();
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //   Toast.makeText(getActivity(), "pull refresh", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        PageNum = 1;
                        doRequest();
                    }
                }, 1000);

            }

            @Override
            public void onfinish() {
                //  Toast.makeText(getActivity(), "finish", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                //     Toast.makeText(getActivity(), "load more", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                        PageNum++;

                        doRequest();
                    }
                }, 1000);
            }
        });
        materialRefreshLayout.autoRefresh();
    }


    @OnClick({R.id.tv_jsks, R.id.tv_jlks, R.id.tv_ahks, R.id.tv_gxks, R.id.tv_title_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jsks:
                tvJsks.setEnabled(false);
                tvJlks.setEnabled(true);
                tvAhks.setEnabled(true);
                tvGxks.setEnabled(true);
                btnKs.setVisibility(View.GONE);
                visibility_Flag = true;
                tvTitleBar.setText("江苏快三");
                table_name = "jsks";
                doRequest();
                break;
            case R.id.tv_jlks:
                tvJsks.setEnabled(true);
                tvJlks.setEnabled(false);
                tvAhks.setEnabled(true);
                tvGxks.setEnabled(true);
                btnKs.setVisibility(View.GONE);
                visibility_Flag = true;
                tvTitleBar.setText("吉林快三");
                table_name = "jlks";
                doRequest();
                break;
            case R.id.tv_ahks:
                tvJsks.setEnabled(true);
                tvJlks.setEnabled(true);
                tvAhks.setEnabled(false);
                tvGxks.setEnabled(true);
                btnKs.setVisibility(View.GONE);
                visibility_Flag = true;
                tvTitleBar.setText("安徽快三");
                table_name = "ahks";
                doRequest();
                break;
            case R.id.tv_gxks:
                tvJsks.setEnabled(true);
                tvJlks.setEnabled(true);
                tvAhks.setEnabled(true);
                tvGxks.setEnabled(false);
                btnKs.setVisibility(View.GONE);
                visibility_Flag = true;
                tvTitleBar.setText("广西快三");
                table_name = "gxks";
                doRequest();
                break;
            case R.id.tv_title_bar:
                if (visibility_Flag) {
                    btnKs.setVisibility(View.VISIBLE);
                    visibility_Flag = false;
                } else {
                    btnKs.setVisibility(View.GONE);
                    visibility_Flag = true;
                }
                break;
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

        String pageSize = "20";
        AlreadyKSAPI api = new AlreadyKSAPI(this, table_name, PageNum, pageSize, new RequestListener() {
            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (KSLotModel) response.model;
                    if (PageNum == 1) {
                        kSlotBeenlist.clear();
                    }
                    kSlotBeenlist.addAll((ArrayList<KSlotBean>) model.datas);
                    Log.e("heihei", kSlotBeenlist.size() + "");
                    ksListAdapter.notifyDataSetChanged();
                } else {
                    model = (KSLotModel) response.model;
                    kSlotBeenlist.clear();
                    ksListAdapter.notifyDataSetChanged();
                }
            }
        });
        api.executeRequest(0);


    }


    @Override
    public void onClick(View v) {
        int i = main.getSystemUiVisibility();
        if (i == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {//2
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else if (i == View.SYSTEM_UI_FLAG_VISIBLE) {//0
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        } else if (i == View.SYSTEM_UI_FLAG_LOW_PROFILE) {//1
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }


}
