package com.myth.cici.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.WindowManager;

public class OthersUtils
{

    public static boolean isChinese(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
        {
            return true;
        }
        return false;
    }

    public static String getFirstChinese(String input)
    {
        for (int i = 0; i < input.length(); i++)
        {
            if (isChinese(input.charAt(i)))
            {
                return input.charAt(i) + "";
            }
        }
        return "";

    }

    public static String readAssertResource(Context context, String strAssertFileName)
    {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try
        {
            InputStream ims = assetManager.open(strAssertFileName);
            strResponse = getStringFromInputStream(ims);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return strResponse;
    }

    private static String getStringFromInputStream(InputStream a_is)
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try
        {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e)
        {
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取屏幕宽度
     * 
     * @param activity
     * @return
     */
    public static int getDisplayWidth(Context context)
    {
        int width = 0;
        if (context == null)
        {
            return 0;
        }
        // 如果context是Activity
        if (context instanceof Activity)
        {
            width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        }
        else
        // 否则，通过系统服务来获取屏幕宽度
        {
            width = getDisplayWidthBySystemService(context);
        }
        return width;
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
