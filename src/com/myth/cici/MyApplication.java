package com.myth.cici;

import java.util.ArrayList;
import java.util.HashMap;

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

    private static HashMap<Integer, ColorEntity> colorMap;

    public static Typeface typeface;

    public static String TypefaceString[] = {"简体", "繁体"};

    @Override
    public void onCreate()
    {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        YunDatabaseHelper.getYunList(this);
        setTypeface(getApplicationContext(), getDefaulTypeface(this));
    }

    public static ColorEntity getColorById(int id)
    {
        if (colorMap == null)
        {
            colorMap = new HashMap<Integer, ColorEntity>();
            ArrayList<ColorEntity> colorList = ColorDatabaseHelper.getAllColor();
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

}
