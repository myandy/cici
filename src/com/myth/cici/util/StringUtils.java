package com.myth.cici.util;

import android.text.TextUtils;

public class StringUtils
{

    public static boolean isEmpty(String s)
    {

        if (TextUtils.isEmpty(s.replaceAll("\n", "").replaceAll(" ", "")))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isNumeric(String str)
    {
        if (TextUtils.isEmpty(str))
        {
            return false;
        }
        for (int i = 0; i < str.length(); i++)
        {
            if (!Character.isDigit(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
}
