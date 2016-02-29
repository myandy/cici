package com.myth.cici.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.myth.cici.R;
import com.myth.cici.util.DisplayUtil;


/** 点点 */
public class SwitchPoint extends LinearLayout
{

    private Context mContext;

    public SwitchPoint(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        mContext = context;

        setOrientation(HORIZONTAL);

        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    }

    /** 按照海报个数，添加点 */
    public void addSwitchBtn(int size)
    {
        addSwitchBtn(size, R.drawable.gc_cover_switcher_dot, DisplayUtil.dip2px(mContext, 4),
                DisplayUtil.dip2px(mContext, 4));
    }

    public void addSwitchBtn(int size, int imageId, int width, int height)
    {
        removeAllViews();

        if (size <= 0)
        {
            return;
        }

        ImageView imageView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        for (int i = 0; i < size; i++)
        {
            imageView = new ImageView(getContext());
            params.setMargins(0, 0, 25, 0);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageId);
            imageView.setEnabled(false);
            addView(imageView);
        }
    }

    /** 设置当前点点 */
    public void setSelectedSwitchBtn(int paramInt)
    {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            getChildAt(i).setEnabled(i == paramInt);
        }
    }

}
