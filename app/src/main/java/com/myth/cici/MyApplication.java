package com.myth.cici;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

import com.myth.cici.db.ColorDatabaseHelper;
import com.myth.cici.db.DBManager;
import com.myth.cici.db.YunDatabaseHelper;
import com.myth.cici.entity.ColorEntity;

public class MyApplication extends Application
{

    private static List<ColorEntity> colorList;

    private static HashMap<Integer, ColorEntity> colorMap;

    private static Typeface typeface;

    public static final String TypefaceString[] = {"简体", "繁体"};

    @Override
    public void onCreate()
    {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        YunDatabaseHelper.getYunList(this);
        setTypeface(getApplicationContext(), getDefaulTypeface(this));
    }

    public static ColorEntity getColorByPos(int pos)
    {
        if (colorList == null)
        {
            colorList = ColorDatabaseHelper.getAll();
        }
        pos--;
        if (pos >= 0 && pos < colorList.size())
        {
            return colorList.get(pos);
        }
        else
        {
            return null;
        }
    }

    public static ColorEntity getColorById(int id)
    {
        if (colorMap == null)
        {
            colorMap = new HashMap<Integer, ColorEntity>();
            ArrayList<ColorEntity> colorList = ColorDatabaseHelper.getAll();
            for (ColorEntity color : colorList)
            {
                colorMap.put(color.getId(), color);
            }
        }
        return colorMap.get(id);
    }

    public static void setTypeface(Context context, int type)
    {
        if (type == 0)
        {
            typeface = Typeface.createFromAsset(context.getAssets(), "fzqkyuesong.TTF");
        }
        else
        {
            typeface = Typeface.createFromAsset(context.getAssets(), "fzsongkebenxiukai_fanti.ttf");
        }
    }

    public static int[] bgimgList = {R.drawable.dust, R.drawable.bg001, R.drawable.bg002, R.drawable.bg004,
            R.drawable.bg006, R.drawable.bg007, R.drawable.bg011, R.drawable.bg013, R.drawable.bg072, R.drawable.bg084,
            R.drawable.bg096, R.drawable.bg118};

    public static int[] bgSmallimgList = {R.drawable.dust, R.drawable.bg001_small, R.drawable.bg002_small,
            R.drawable.bg004_small, R.drawable.bg006_small, R.drawable.bg007_small, R.drawable.bg011_small,
            R.drawable.bg013_small, R.drawable.bg072_small, R.drawable.bg084_small, R.drawable.bg096_small,
            R.drawable.bg118_small};

    public static void setDefaultTypeface(Context context, int i)
    {
        if (i < 2 && i >= 0)
        {
            Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.putInt("typeface", i);
            edit.commit();
        }
    }

    public static int getDefaulTypeface(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("typeface", 0);
    }

    public static boolean getDefaulListType(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("listType", true);
    }

    public static void setDefaultListType(Context context, boolean bool)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("listType", bool);
        edit.commit();
    }

    public static boolean getCheckAble(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("check", true);
    }

    public static void setCheckAble(Context context, boolean bool)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("check", bool);
        edit.commit();
    }

    public static int getDefaultTextSize(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("size", 18);
    }

    public static void setDefaultTextSize(Context context, int size)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("size", size);
        edit.commit();
    }

    public static int getDefaultShareSize(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("share_size", 18);
    }

    public static void setDefaultShareSize(Context context, int size)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("share_size", size);
        edit.commit();
    }

    public static boolean getDefaultShareGravity(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("share_gravity", true);
    }

    public static void setDefaultShareGravity(Context context, boolean iscenter)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("share_gravity", iscenter);
        edit.commit();
    }

    public static boolean getDefaultShareAuthor(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("share_author", true);
    }

    public static void setDefaultShareAuthor(Context context, boolean iscenter)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("share_author", iscenter);
        edit.commit();
    }

    public static int getDefaultSharePadding(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("share_padding", 20);
    }

    public static void setDefaultSharePadding(Context context, int size)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("share_padding", size);
        edit.commit();
    }

    public static int getDefaultShareColor(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("share_color", 0);
    }

    public static void setDefaultShareColor(Context context, int size)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("share_color", size);
        edit.commit();
    }

    public static String getDefaultUserName(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("username", "");
    }

    public static void setDefaultUserName(Context context, String size)
    {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString("username", size);
        edit.commit();
    }

    public static Typeface getTypeface()
    {
        return typeface;
    }

    public static void setTypeface(Typeface typeface)
    {
        MyApplication.typeface = typeface;
    }

}
