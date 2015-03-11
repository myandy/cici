package com.myth.cici.util;

import android.content.Context;

public class ResizeUtil
{
    /**
     * 宽720
     */
    private static final int W720 = 720;

    public static float sysWidth;

    public static int resize(Context context, float origin)
    {
        if (sysWidth == 0)
        {
            sysWidth = OthersUtils.getDisplayWidth(context);
        }
        return (int) (origin * sysWidth / W720);
    }
}
