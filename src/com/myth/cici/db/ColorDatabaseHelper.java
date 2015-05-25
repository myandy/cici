package com.myth.cici.db;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myth.cici.entity.ColorEntity;

public class ColorDatabaseHelper
{
    private static final String TABLE_NAME = "color";


    public static ArrayList<ColorEntity> getAll()
    {
        SQLiteDatabase db = DBManager.getDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME  +" order by displayidx", null);
        return getColorListFromCursor(cursor);
    }

    private static ArrayList<ColorEntity> getColorListFromCursor(Cursor cursor)
    {
        ArrayList<ColorEntity> list = new ArrayList<ColorEntity>();
        while (cursor.moveToNext())
        {
            ColorEntity color = new ColorEntity();
            color.setId(cursor.getInt(cursor.getColumnIndex("id")));
            color.setRed(cursor.getInt(cursor.getColumnIndex("red")));
            color.setGreen(cursor.getInt(cursor.getColumnIndex("green")));
            color.setBlue(cursor.getInt(cursor.getColumnIndex("blue")));
            color.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(color);
        }
        return list;
    }

}
