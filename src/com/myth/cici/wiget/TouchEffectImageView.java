package com.myth.cici.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * User: youxueliu Date: 2014/6/23 Time: 14:40 有点击效果的ImageView
 */
public class TouchEffectImageView extends ImageView
{

    public final static int TOUCH_ALPHA = 100; // 点击的时候设置透明度

    public final static int UP_ALPHA = 255; // 弹起的时候恢复透明度

    private boolean mDoesntSetAlpha; // 不设置透明度

    /**
     * 是否需要设置点击时候按钮背景色变化
     * 
     * @param setAlphaState
     */
    public void setDontShowAlphaState(boolean setAlphaState)
    {
        mDoesntSetAlpha = setAlphaState;
    }

    public TouchEffectImageView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
    }

    @Override
    protected void drawableStateChanged()
    {
        super.drawableStateChanged();
        if (!mDoesntSetAlpha)
        {
            if (isPressed())
                setAlpha(TOUCH_ALPHA);
            else
                setAlpha(UP_ALPHA);
        }
    }
}
