package com.myth.cici.db;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myth.cici.entity.Ci;

public class CiDatabaseHelper
{
    private static String TABLE_NAME = "example";

    public static ArrayList<Ci> getCiByCipaiId(int cipaiId)
    {
        SQLiteDatabase db = DBManager.getDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where ci_id=" + cipaiId, null);
        return getCipaiListFromCursor(cursor);
    }

    private static ArrayList<Ci> getCipaiListFromCursor(Cursor cursor)
    {
        ArrayList<Ci> list = new ArrayList<Ci>();
        while (cursor.moveToNext())
        {
            Ci ci = new Ci();
            ci.setId(cursor.getInt(cursor.getColumnIndex("id")));
            ci.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            ci.setNote(cursor.getString(cursor.getColumnIndex("note")));
            ci.setText(cursor.getString(cursor.getColumnIndex("text")));
            list.add(ci);
        }
        return list;
    }

}
