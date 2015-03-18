package com.myth.cici.wiget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PingzeLinearlayout extends LinearLayout
{

    public PingzeLinearlayout(Context context, String code)
    {
        super(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(25, 50);
        setVerticalGravity(HORIZONTAL);
        for (int i = 0; i < code.length(); i++)
        {
            char c = code.charAt(i);
            if ('0' < c && c <= '9')
            {
                addView(new PingzeView(context, c - '0'), layoutParams);
            }
            else if (!TextUtils.isEmpty(c + "") && c != '\n')
            {
                TextView tv = new TextView(context);
                tv.setText(c + "");
                addView(tv, lps);
            }
        }
    }

}
