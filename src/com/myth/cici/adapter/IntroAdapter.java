package com.myth.cici.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.myth.cici.util.ResizeUtil;

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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            LinearLayout.LayoutParams colorLayoutParams = new LinearLayout.LayoutParams(
                    ResizeUtil.resize(mContext, 450), ResizeUtil.resize(mContext, 540));

            convertView = new LinearLayout(mContext);
            holder.imageview = new ImageView(mContext);

            holder.imageview.setScaleType(ScaleType.FIT_XY);
            holder.imageview.setLayoutParams(colorLayoutParams);
            ((LinearLayout) convertView).addView(holder.imageview);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageview.setImageResource(mColors[position]);

        return convertView;
    }

    public class ViewHolder
    {
        ImageView imageview;
    }

}
