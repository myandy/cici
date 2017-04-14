package com.myth.cici.wiget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.R;
import com.myth.cici.util.DisplayUtil;

public class PingzeLinearlayout extends LinearLayout {

    public PingzeLinearlayout(Context context, String code) {
        super(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(context, 20),
                DisplayUtil.dip2px(context, 20));
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(DisplayUtil.dip2px(context, 10),
                DisplayUtil.dip2px(context, 20));
        setVerticalGravity(HORIZONTAL);
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if ('0' < c && c <= '9') {
                addView(new PingzeView(context, c - '0'), layoutParams);
            } else if (!TextUtils.isEmpty((c + "").trim())) {
                TextView tv = new TextView(context);
                tv.setTextColor(getResources().getColor(R.color.black_light));
                tv.setText(c + "");
                addView(tv, lps);
            }
        }
    }

}
