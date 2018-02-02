package com.jdruanjian.jdks.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jdruanjian.jdks.BaseFragment;
import com.jdruanjian.jdks.Constants;
import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.model.basicmodel.GetPriceModel;
import com.jdruanjian.jdks.model.basicmodel.OrderPriceModel;
import com.jdruanjian.jdks.model.basicmodel.ReChargeModel;
import com.jdruanjian.jdks.ui.activity.AboutUsActivity;
import com.jdruanjian.jdks.ui.activity.FindPassWordActivity;
import com.jdruanjian.jdks.ui.activity.LoginActivity;
import com.jdruanjian.jdks.ui.activity.Result_KS_Activity;
import com.jdruanjian.jdks.utils.DataCleanManager;
import com.jdruanjian.jdks.utils.NetworkUtils;
import com.jdruanjian.jdks.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Longlong on 2017/12/19.
 */

public class Fragment3 extends BaseFragment {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    Unbinder unbinder;
    @BindView(R.id.et_card_num)
    EditText etCardNum;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_endtime)
    TextView tvEndtime;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_halfyear)
    TextView tvHalfyear;
    @BindView(R.id.tv_yaer)
    TextView tvYaer;
    @BindView(R.id.tv_forever)
    TextView tvForever;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    private GetPriceModel model;
    private Dialog dialog;
    DataCleanManager dataClean;
    private OrderPriceModel orderPriceModel;
    private ReChargeModel reChargeModel;
    private String endtime;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment_3, container, false);
        unbinder = ButterKnife.bind(this, layout);
        setBar(layout);
        setTitlebar(layout, "我的");
        tvUsername.setText(PrefUtils.getString(getActivity().getApplication(), "name", null));
        tvEndtime.setText(PrefUtils.getString(getActivity().getApplication(), "endtime", null));
        doRequest();
        getBuyOrder();
        dataClean = new DataCleanManager();
        try {
            String num = dataClean.getTotalCacheSize(getActivity());
            if(num != null|| !num.equals("")){
                tvClear.setText(num);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_calculate,R.id.tv_week, R.id.tv_month, R.id.tv_halfyear, R.id.tv_yaer, R.id.tv_forever, R.id.btn_ensure_buy, R.id.btn_history, R.id.btn_modifypwd, R.id.btn_satement, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_week:

                Uri uri = Uri.parse(orderPriceModel.data.url_1);
                Intent intent0 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent0);
                break;
            case R.id.tv_month:

                Uri uri1 = Uri.parse(orderPriceModel.data.url_2);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent1);
                break;
            case R.id.tv_halfyear:

                Uri uri2 = Uri.parse(orderPriceModel.data.url_3);
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                break;
            case R.id.tv_yaer:

                Uri uri3 = Uri.parse(orderPriceModel.data.url_4);
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
                startActivity(intent3);
                break;
            case R.id.tv_forever:

                Uri uri4 = Uri.parse(orderPriceModel.data.url_5);
                Intent intent4 = new Intent(Intent.ACTION_VIEW, uri4);
                startActivity(intent4);
                break;
            case R.id.btn_ensure_buy:
                if (!TextUtils.isEmpty(etCardNum.getText().toString())) {
                    getReCharge();
                    etCardNum.setText("");
                } else {
                    Toast.makeText(getActivity().getApplication(), "请输入卡号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_history:
                Intent intental = new Intent(getActivity(), Result_KS_Activity.class);
                startActivity(intental);
                break;
            case R.id.btn_calculate:
                dataClean.clearAllCache(getActivity());
                try {
                    String num = dataClean.getTotalCacheSize(getActivity());
                    if (num != null || !num.equals("")) {
                        tvClear.setText(num);
                        Toast.makeText(getActivity().getApplication(), "清理成功", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.btn_modifypwd:
                Intent intentft = new Intent(getActivity(), FindPassWordActivity.class);
                startActivity(intentft);
                break;
            case R.id.btn_satement:
                Intent intentab = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intentab);
                break;
            case R.id.btn_logout:
                showdialog1();
                break;
        }
    }

    public void getBuyOrder() {
        if (isLoading) {
            return;
        }
        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }
        OkGo.post(Constants.ORDER_CARDNUM)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        orderPriceModel = JSON.parseObject(s.getBytes(), OrderPriceModel.class);
                        if (orderPriceModel.status.equals("0")) {
                            String Name = orderPriceModel.data.url_1;
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

    public void getReCharge() {
        if (isLoading) {
            return;
        }
        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }
        OkGo.post(Constants.RECHARGE)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("uid", PrefUtils.getString(getActivity().getApplication(), "uid", null))
                .params("code", etCardNum.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        reChargeModel = JSON.parseObject(s.getBytes(), ReChargeModel.class);
                        if (reChargeModel.status.equals("0")) {
                            tvEndtime.setText(reChargeModel.msg);
                            endtime = reChargeModel.msg;
                            saveUserMsg();
                            Toast.makeText(getActivity().getApplication(), "充值成功", Toast.LENGTH_SHORT).show();

                        } else if (reChargeModel.status.equals("1")) {
                            Toast.makeText(getActivity().getApplication(), reChargeModel.msg, Toast.LENGTH_SHORT).show();
                            tvEndtime.setText(PrefUtils.getString(getActivity().getApplication(), "endtime", null));
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        Toast.makeText(getActivity().getApplication(), "数据有误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserMsg() {
        PrefUtils.putString(getActivity().getApplication(), "endtime", endtime);
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
        OkGo.post(Constants.GET_PRICE)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appId", "1")
                .execute(new StringCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        model = JSON.parseObject(s.getBytes(), GetPriceModel.class);
                        if (model.status.equals("0")) {
                            tvWeek.setText(model.data.priceWeek + "元/一周");
                            tvMonth.setText(model.data.priceMonth + "元/一月");
                            tvHalfyear.setText(model.data.priceHalfYear + "元/半年");
                            tvYaer.setText(model.data.priceYear + "元/一年");
                            tvForever.setText(model.data.priceForever + "元/永久");
                            tvService.setText(model.data.qq);
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

    private void showdialog1() {

        View localView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_logout, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
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
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataClean.clearAllCache(getActivity());
                try {
                    String num = dataClean.getTotalCacheSize(getActivity());

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                SharedPreferences userSettings = getActivity().getSharedPreferences("JDLot", 0);
                SharedPreferences.Editor editor = userSettings.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                finishActivity();
                dialog.dismiss();
            }
        });
    }
}
