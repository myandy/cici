package com.myth.cici.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.myth.cici.entity.Yun;

public class YunDatabaseHelper
{
    private static String YUNSHU[] = {"zhonghuaxinyun", "pingshuiyun", "cilinzhengyun"};

    public static String YUNString[] = {"中华新韵", "平水韵", "词林正韵"};

    private static ArrayList<Yun> yunList;

    public static void getYunList(Context context)
    {
        SQLiteDatabase db = DBManager.getNewDatabase();
        Cursor cursor = db.rawQuery("select * from " + YUNSHU[getDefaultYunShu(context)], null);
        yunList = getYunListFromCursor(cursor);
    }

    /**
     * 获取字的平仄<一句话功能简述> <功能详细描述>
     * 
     * @param word
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getWordStone(String word)
    {
        List<Integer> tones = new ArrayList<Integer>();
        for (int i = 0; i < yunList.size(); i++)
        {
            if (yunList.get(i).getGlys().contains(word))
            {
                tones.add(yunList.get(i).getTone());
            }
        }
        if (tones.size() == 1)
        {
            return tones.get(0);
        }
        else
        {
            return 30;
        }
    }

    /**
     * 获取同韵字<一句话功能简述> <功能详细描述>
     * 
     * @param word
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<Yun> getSameYun(String word)
    {
        List<Yun> yuns = new ArrayList<Yun>();
        for (int i = 0; i < yunList.size(); i++)
        {
            if (yunList.get(i).getGlys().contains(word))
            {
                yuns.add(yunList.get(i));
            }
        }
        return yuns;
    }

    private static ArrayList<Yun> getYunListFromCursor(Cursor cursor)
    {
        ArrayList<Yun> list = new ArrayList<Yun>();
        while (cursor.moveToNext())
        {
            Yun ci = new Yun();
            ci.setTone(cursor.getInt(cursor.getColumnIndex("tone")));
            ci.setGlys(cursor.getString(cursor.getColumnIndex("glys")));
            ci.setSection_desc(cursor.getString(cursor.getColumnIndex("section_desc")));
            ci.setTone_name(cursor.getString(cursor.getColumnIndex("tone_name")));
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
            getYunList(context);
        }
    }

    public static int getDefaultYunShu(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("yunshu", 0);
    }

}
