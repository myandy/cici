package com.myth.cici.adapter;

import com.myth.cici.util.ResizeUtil;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class IntroAdapter extends BaseAdapter
{

    private Context mContext;

    private int[] mColors;

    public IntroAdapter(Context context, int[] colors)
    {
        mContext = context;
        mColors = colors;
    }

    public int getCount()
    {
        return mColors == null ? 0 : mColors.length;
    }

    public Object getItem(int position)
    {
        return mColors == null ? null : mColors[position];
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View cacheView, ViewGroup parent)
    {
        LinearLayout.LayoutParams colorLayoutParams = new LinearLayout.LayoutParams(ResizeUtil.resize(mContext, 650), ResizeUtil.resize(mContext, 800));
        ImageView imageview = new ImageView(mContext);
        imageview.setImageResource(mColors[position]);
        imageview.setScaleType(ScaleType.FIT_XY);
        imageview.setLayoutParams(colorLayoutParams);

        return imageview;
    }

}
