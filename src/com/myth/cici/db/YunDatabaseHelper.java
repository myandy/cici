package com.myth.cici.db;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.myth.cici.entity.Yun;

public class YunDatabaseHelper
{
    private static String YUNSHU[] = {"zhonghuaxinyun", "pingshuiyun", "cilinzhenyun"};

    private static ArrayList<Yun> yunList;

    public static void getYunList(Context context)
    {
        if (yunList == null || yunList.size() == 0)
        {
            SQLiteDatabase db = DBManager.getDatabase();
            Cursor cursor = db.rawQuery("select * from " + YUNSHU[getDefaultYunShu(context)], null);
            yunList = getYunListFromCursor(cursor);
        }
    }

    public static int getWordStone(String word)
    {
        for (int i = 0; i < yunList.size(); i++)
        {
            if (yunList.get(i).getGlys().contains(word))
            {
                return yunList.get(i).getTone();
            }
        }
        return 10;
    }

    private static ArrayList<Yun> getYunListFromCursor(Cursor cursor)
    {
        ArrayList<Yun> list = new ArrayList<Yun>();
        while (cursor.moveToNext())
        {
            Yun ci = new Yun();
            ci.setTone(cursor.getInt(cursor.getColumnIndex("tone")));
            ci.setGlys(cursor.getString(cursor.getColumnIndex("glys")));
            list.add(ci);
        }
        return list;
    }

    public static void setDefaultYunShu(Context context, int i)
    {

        if (i < YUNSHU.length && i >= 0)
        {
            Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.putInt("yunshu", i);
            edit.commit();
        }
    }

    public static int getDefaultYunShu(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("yunshu", 0);
    }

}
