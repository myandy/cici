package com.myth.cici.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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

    private static ResizeUtil instance;

    public static ResizeUtil getInstance() {
        if (instance == null) {
            synchronized (ResizeUtil.class) {
                if (instance == null) {
                    instance = new ResizeUtil();
                }
            }
        }
        return instance;
    }


    public void init(Context context) {
        if (sysWidth == 0) {
            sysWidth = OthersUtils.getDisplayWidth(context);
        }
    }

    public int resize(float origin) {

        return (int) (origin * sysWidth / W720);
    }

    public void layoutSquareView(View itemContainer) {
        ViewGroup.LayoutParams params = itemContainer.getLayoutParams();
        params.width = resize(540);
        params.height = resize(540);
        itemContainer.setLayoutParams(params);
    }
}
