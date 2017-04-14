package com.myth.cici.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class IntroAdapter extends BaseAdapter {

    private Context mContext;

    private int[] mImages;

    public IntroAdapter(Context context, int[] images) {
        mContext = context;
        mImages = images;
    }

    public int getCount() {
        return mImages == null ? 0 : mImages.length;
    }

    public Object getItem(int position) {
        return mImages == null ? null : mImages[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = new LinearLayout(mContext);
            holder.imageview = new ImageView(mContext);

            holder.imageview.setScaleType(ScaleType.FIT_XY);
            ((LinearLayout) convertView).addView(holder.imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageview.setImageResource(mImages[position]);

        return convertView;
    }

    public class ViewHolder {
        ImageView imageview;
    }

}
