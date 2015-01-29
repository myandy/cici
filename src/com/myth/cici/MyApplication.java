package com.myth.cici;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.myth.cici.db.ColorDatabaseHelper;
import com.myth.cici.db.DBManager;
import com.myth.cici.entity.ColorEntity;

public class MyApplication extends Application
{

    private static HashMap<Integer, ColorEntity> colorMap;

    public static Typeface typeface;

    @Override
    public void onCreate()
    {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        typeface = getTypeface(getApplicationContext(), 1);
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

    public static Typeface getTypeface(Context context, int type)
    {
        if (type == 1)
        {
            return Typeface.createFromAsset(context.getAssets(), "fzqkyuesong.TTF");
        }
        else
        {
            return Typeface.createFromAsset(context.getAssets(), "fzsongkebenxiukai_fanti.ttf");
        }
    }

}
