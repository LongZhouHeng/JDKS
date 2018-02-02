package com.jdruanjian.jdks.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.model.entity.KSlotBean;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/9/6.
 */

public class SSListAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<KSlotBean> kSlotBeen;

    public SSListAdapter(Activity mActivity, ArrayList<KSlotBean> kSlotBeen) {

        this.mActivity = mActivity;
        this.kSlotBeen = kSlotBeen;
    }

    @Override
    public int getCount() {
        return kSlotBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return kSlotBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_ss, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final KSlotBean ssLotBean = kSlotBeen.get(position);
        String str = ssLotBean.getPeriod();
        String period = str.substring(8);
        holder.tv_numperiod.setText(period);
        String date = ssLotBean.getTime_current();
        String min = date.substring(8,10);
        String second = date.substring(10,12);
        String Date = min+":"+second;
        holder.tv_date.setText(Date);
        String num = ssLotBean.getNumber();
        String one = num.substring(0,1);
        String two = num.substring(1,2);
        String three = num.substring(2,3);
        String four = num.substring(3,4);
        String five = num.substring(4);
        holder.tv_ks1.setText(one);
        holder.tv_ks2.setText(two);
        holder.tv_ks3.setText(three);
        holder.tv_ks4.setText(four);
        holder.tv_ks5.setText(five);
        return convertView;
    }


    public static class ViewHolder {

        TextView tv_numperiod;
        TextView tv_date;
        TextView tv_ks1;
        TextView tv_ks2;
        TextView tv_ks3;
        TextView tv_ks4;
        TextView tv_ks5;
        private static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_numperiod = (TextView) view.findViewById(R.id.tv_numperiod);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_kstime);
            holder.tv_ks1 = (TextView) view.findViewById(R.id.tv_ks1);
            holder.tv_ks2 = (TextView) view.findViewById(R.id.tv_ks2);
            holder.tv_ks3 = (TextView) view.findViewById(R.id.tv_ks3);
            holder.tv_ks4 = (TextView) view.findViewById(R.id.tv_ks4);
            holder.tv_ks5 = (TextView) view.findViewById(R.id.tv_ks5);
            view.setTag(holder);
            return holder;
        }
    }



}
