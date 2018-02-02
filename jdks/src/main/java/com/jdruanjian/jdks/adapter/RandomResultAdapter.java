package com.jdruanjian.jdks.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jdruanjian.jdks.R;
import com.jdruanjian.jdks.model.entity.ResultListBean;

import java.util.ArrayList;

/**
 * Created by Longlong on 2017/12/7.
 */

public class RandomResultAdapter extends BaseAdapter {

   //实例化的数组或者集合
    private ArrayList<ResultListBean> resulrlist;

    private Activity mActivity;

    public RandomResultAdapter(FragmentActivity mActivity, ArrayList<ResultListBean> resulrlist) {

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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_result, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //取值
        final  ResultListBean addList = resulrlist.get(position);

        holder.tv_academys.setText(addList.planName);
 /*  //     这里不用循环也可以的，因为getView方法本身就会去循环listView来画item，这里的
//        循环i和position是等价的，i的 值就是position的值
        for (int i = 0; i < getCount(); i++) {
            if (position == i) {
                if (i % 2 == 0) {
                    int color = Color.argb(255, 255, 255, 255);
                    holder.tv_academys.setBackgroundColor(color);
                } else {
                    int color = Color.argb(255, 101, 181, 109);
                    holder.tv_academys.setBackgroundColor(color);
                }
            }
        }*/
                return convertView;

        }


    public static class ViewHolder {


        TextView tv_academys;
        TextView tv_majors;
        TextView tv_score1;


        private static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_academys = (TextView) view.findViewById(R.id.tv_academys);
            view.setTag(holder);
            return holder;
        }
    }



}
