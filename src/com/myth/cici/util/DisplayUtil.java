package com.myth.cici.util;

import android.content.Context;

public class DisplayUtil
{
    public static int dip2px(Context context, double dipValue)
    {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public static int px2dip(Context context, double pxValue)
    {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    /**
     * 长的为宽度
     * <功能详细描述>
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int screenWidthPx(Context context)
    {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? widthPx : heightPx;
    }
    
    /**
     * 小的为高度
     * @param context
     * @return
     */
    public static int screenHeightPx(Context context)
    {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? heightPx : widthPx;
    }
}
