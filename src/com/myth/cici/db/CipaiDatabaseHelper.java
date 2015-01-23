package com.myth.cici.db;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myth.cici.entity.Cipai;

public class CipaiDatabaseHelper
{
    private static String TABLE_NAME = "cipai";

    public static ArrayList<Cipai> getAllCipai()
    {
        SQLiteDatabase db = DBManager.getDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return getCipaiListFromCursor(cursor);
    }

    private static ArrayList<Cipai> getCipaiListFromCursor(Cursor cursor)
    {
        ArrayList<Cipai> list = new ArrayList<Cipai>();
        while (cursor.moveToNext())
        {
            Cipai cipai = new Cipai();
            cipai.setId(cursor.getInt(cursor.getColumnIndex("id")));
            cipai.setAlias(cursor.getString(cursor.getColumnIndex("alias")));
            cipai.setColor_id(cursor.getInt(cursor.getColumnIndex("color_id")));
            cipai.setEnname(cursor.getString(cursor.getColumnIndex("enname")));
            cipai.setName(cursor.getString(cursor.getColumnIndex("name")));
            cipai.setParent_id(cursor.getInt(cursor.getColumnIndex("parent_id")));
            cipai.setPingze(cursor.getString(cursor.getColumnIndex("pingze")));
            cipai.setSource(cursor.getString(cursor.getColumnIndex("source")));
            cipai.setTone_type(cursor.getInt(cursor.getColumnIndex("tone_type")));
            cipai.setWordcount(cursor.getInt(cursor.getColumnIndex("wordcount")));
            list.add(cipai);
        }
        return list;
    }

}
