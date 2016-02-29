package com.myth.cici.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.text.ClipboardManager;
import android.view.View;
import android.view.WindowManager;

public class OthersUtils
{

    /**
     * 实现文本复制功能 add by wangqianzhou
     * 
     * @param content
     */
    public static void copy(String content, Context context)
    {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能 add by wangqianzhou
     * 
     * @param context
     * @return
     */
    public static String paste(Context context)
    {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    /**
     * 分享功能
     * 
     * @param context 上下文
     * @param activityTitle Activity的名字
     * @param msgTitle 消息标题
     * @param msgText 消息内容
     * @param imgPath 图片路径，不分享图片则传null
     */
    public static void shareMsg(Context context, String activityTitle, String msgTitle, String msgText, String imgPath)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals(""))
        {
            intent.setType("text/plain"); // 纯文本
        }
        else
        {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile())
            {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }

    public static Bitmap createViewBitmap(View v)
    {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    public static boolean isChinese(char c)
    {

        String chinese = "[\u4e00-\u9fa5]";
        if ((c + "").matches(chinese))
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
