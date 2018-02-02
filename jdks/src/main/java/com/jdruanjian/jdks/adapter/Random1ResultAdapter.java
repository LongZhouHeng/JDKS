package com.jdruanjian.jdks.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.model.entity.PlanRankBean;

import java.util.ArrayList;

/**
 * Created by Longlong on 2017/12/7.
 */

public class Random1ResultAdapter extends BaseAdapter {

    //实例化的数组或者集合
    private ArrayList<PlanRankBean> resulrlist;

    private Activity mActivity;

    public Random1ResultAdapter(FragmentActivity mActivity, ArrayList<PlanRankBean> resulrlist) {

        this.mActivity = mActivity;
        this.resulrlist = resulrlist;
    }

    @Override
    public int getCount() {
        return resulrlist.size();
    }

    @Override
    public Object getItem(int position) {
        return resulrlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            //item的布局
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_result1, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //取值
        final  PlanRankBean addList = resulrlist.get(position);

        holder.tv_academys.setText(addList.planName);
        holder.checkNum.setText(addList.checkNum);
        //   这里不用循环也可以的，因为getView方法本身就会去循环listView来画item，这里的
        //   循环i和position是等价的，i的 值就是position的值
        for (int i = 0; i < getCount(); i++) {
            if (position == i) {
                if (i % 2 == 0) {
                    int color = Color.argb(255, 255, 255, 255);
                    holder.ll_item.setBackgroundColor(color);
                } else {
                    int color = Color.argb(255, 253, 245, 227);
                    holder.ll_item.setBackgroundColor(color);
                }
            }
        }
        if (position ==0){
            holder.iv_rank.setBackgroundResource(R.drawable.pic_rank_1);
        }
        if (position ==1){
            holder.iv_rank.setBackgroundResource(R.drawable.pic_rank_2);
        }
        if (position ==2){
            holder.iv_rank.setBackgroundResource(R.drawable.pic_rank_3);
        }
        return convertView;

    }


    public static class ViewHolder {


        TextView tv_academys,checkNum;
        RelativeLayout ll_item;
        ImageView iv_rank;


        private static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_academys = (TextView) view.findViewById(R.id.tv_academys);
            holder.ll_item = view.findViewById(R.id.ll_item);
            holder.iv_rank = view.findViewById(R.id.iv_rank);
            holder.checkNum = view.findViewById(R.id.tv_checknum);
            view.setTag(holder);
            return holder;
        }
    }



}
