package com.myth.cici.util;

import android.widget.EditText;

public class CheckUtils
{

    public static void checkEditText(EditText edittext, String s)
    {
        String test = edittext.getEditableText().toString();
        int pos = 0;
        s = s.replaceAll("\\D", "");
        for (int i = 0; i < test.length(); i++)
        {
            if (OthersUtils.isChinese(test.charAt(i)))
            {
            	pos++;
                if (getHanziCode(test.charAt(i)) != s.charAt(pos))
                {
                	//设置删除线
                }
            }
        }
    }


    public static int getHanziCode(char c)
    {

        return 1;

    }

    public static String[] getCodeFormPingze(String[] list)
    {
        if (list != null && list.length > 0)
        {
            String[] codes = new String[list.length - 1];
            for (int i = 0; i < list.length - 1; i++)
            {
                codes[i] = "";
                for (int j = 0; j < list[i].length(); j++)
                {
                    if (list[i].charAt(j) == '平')
                    {
                        codes[i] += "1";
                    }
                    else if (list[i].charAt(j) == '中')
                    {
                        codes[i] += "2";
                    }
                    else if (list[i].charAt(j) == '仄')
                    {
                        codes[i] += "3";
                    }
                    else if (list[i].charAt(j) == '（' && list[i].charAt(j + 1) == '韵' && list[i].charAt(j + 2) == '）')
                    {
                        int x = Integer.parseInt(codes[i].charAt(codes[i].length() - 1) + "") + 3;
                        codes[i] = codes[i].substring(0, codes[i].length() - 1) + x;
                    }
                    else if (list[i].charAt(j) == '（' || list[i].charAt(j) == '韵' || list[i].charAt(j) == '）')
                    {

                    }
                    else
                    {
                        codes[i] += list[i].charAt(j);
                    }
                }

            }
            return codes;
        }
        return null;
    }
}
