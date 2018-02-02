package com.jdruanjian.jdks.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


import com.jdruanjian.jdks.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部导航
 */
public class BottomBar extends LinearLayout implements OnClickListener {

    public static final int TAG_0 = 0;
    public static final int TAG_1 = 1;
    public static final int TAG_2 = 2;
    public static final int TAG_3 = 3;
    public static final int TAG_4 = 4;
    private Context mContext;

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public BottomBar(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private List<View> itemList;

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.layout_bottom_bar, null);
        layout.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f));
        Button btn1 = (Button) layout.findViewById(R.id.btn_item_1);
        Button btn2 = (Button) layout.findViewById(R.id.btn_item_2);
        Button btn3 = (Button) layout.findViewById(R.id.btn_item_3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        btn1.setTag(TAG_0);
        btn2.setTag(TAG_1);
        btn3.setTag(TAG_2);

        itemList = new ArrayList<View>();
        itemList.add(btn1);
        itemList.add(btn2);
        itemList.add(btn3);
        this.addView(layout);
    }

    @Override
    public void onClick(View v) {
        int tag = (Integer) v.getTag();
        switch (tag) {
        case TAG_0:
        case TAG_1:
        case TAG_2:
        case TAG_3:
        case TAG_4:
            setNormalState(lastButton);
            setSelectedState(tag);
            break;
        }
    }

    private int lastButton = -1;

    public void setSelectedState(int index) {
        if (index != -1 && onItemChangedListener != null) {
            if (index > itemList.size()) {
                throw new RuntimeException(
                        "the value of default bar item can not bigger than string array's length");
            }
            itemList.get(index).setSelected(true);
            onItemChangedListener.onItemChanged(index);
            lastButton = index;
        }
    }

    public void setNormalState(int index) {
        if (index != -1) {
            if (index > itemList.size()) {
                throw new RuntimeException(
                        "the value of default bar item can not bigger than string array's length");
            }
            itemList.get(index).setSelected(false);
        }
    }

    public interface OnItemChangedListener {
        public void onItemChanged(int index);
    }

    private OnItemChangedListener onItemChangedListener;

    public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
        this.onItemChangedListener = onItemChangedListener;
    }
}
