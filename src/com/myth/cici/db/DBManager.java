package com.myth.cici.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.myth.cici.R;

public class DBManager {
    private final static int BUFFER_SIZE = 400000;

    public static final String DB_NAME = "ci.db"; // 保存的数据库文件名，今天用于保存用户数据

    public static final String DB_NEW_NAME = "ci_new.db"; // 新的数据库文件名，只保存内容数据

    public static final String PACKAGE_NAME = "com.myth.cici";

    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/" + DB_NAME; // 在手机里存放数据库的位置

    public static final String DB_NEW_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/" + DB_NEW_NAME; // 在手机里存放数据库的位置

    /** The Constant VERSION. */
    public static final int DB_VERSION = 3;

    private static SQLiteDatabase db_new;

    private static SQLiteDatabase db;

    public static void initDatabase(Context context) {
        try {
            File file = new File(DB_NEW_PATH);
            boolean copy = true;
            if (file.exists()) {
                if (getNewDatabase().getVersion() >= DB_VERSION) {
                    copy = false;
                }
            }
            if (copy) {
                // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = context.getResources().openRawResource(
                        R.raw.ci_new); // 欲导入的数据库
                FileOutputStream fos = new FileOutputStream(DB_NEW_PATH);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();

                getNewDatabase().setVersion(DB_VERSION);
            }
            if (!new File(DB_PATH).exists()) {

                if (!BackupTask.restoreDatabase(context)) {
                    // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                    InputStream is = context.getResources().openRawResource(
                            R.raw.ci); // 欲导入的数据库
                    FileOutputStream fos = new FileOutputStream(DB_PATH);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();
                }
            }

        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
    }

    public static SQLiteDatabase getNewDatabase() {
        if (db_new == null) {
            db_new = SQLiteDatabase.openOrCreateDatabase(DB_NEW_PATH, null);
        }
        return db_new;
    }

    public static SQLiteDatabase getDatabase() {
        if (db == null) {
            db = SQLiteDatabase.openOrCreateDatabase(DB_PATH, null);
        }
        return db;
    }

}
