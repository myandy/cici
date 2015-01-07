package com.myth.cici.util;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class ResizeUtil
{
    /**
     * 宽720
     */
    private static final int W720 = 720;

    public static int resize(Activity context, float origin)
    {
        float sysWidth;
        // 如果context是Activity
        if (context instanceof Activity)
        {
            sysWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        }
        else
        // 否则，通过系统服务来获取屏幕宽度
        {
            sysWidth = getDisplayWidthBySystemService(context);
        }
        return (int) (origin * sysWidth / W720);
    }

    /**
     * 获取屏幕宽度(通过系统服务)
     * 
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static int getDisplayWidthBySystemService(Context context)
    {
        int width = 0;
        try
        {
            WindowManager wm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
            width = wm.getDefaultDisplay().getWidth();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return width;
    }
}
