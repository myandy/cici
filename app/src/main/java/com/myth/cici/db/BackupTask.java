package com.myth.cici.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.myth.cici.util.Logs;

public class BackupTask extends AsyncTask<String, Void, Integer> {
    public static final String COMMAND_BACKUP = "backupDatabase";
    public static final String COMMAND_RESTORE = "restoreDatabase";
    private Context mContext;
    public static boolean needBackup;

    public BackupTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        // 获得正在使用的数据库路径，我的是 sdcard 目录下的 /dlion/db_dlion.db
        // 默认路径是 /data/data/(包名)/databases/*.db

        String dbPath = "/data"
                + Environment.getDataDirectory().getAbsolutePath() + "/"
                + mContext.getPackageName();
        File dbFile = mContext
                .getDatabasePath(dbPath + "/" + DBManager.DB_NAME);
        File exportDir = new File(Environment.getExternalStorageDirectory()
                .getPath(), "cici");

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        if (!new File(dbPath).exists()) {
            new File(dbPath).mkdirs();
        }
        File backup = new File(exportDir, dbFile.getName());
        String command = params[0];
        Logs.error("BackUpTask:" + command);
        if (command.equals(COMMAND_BACKUP)) {
            if (needBackup) {
                needBackup = false;
                if (dbFile.exists()) {
                    try {
                        backup.createNewFile();
                        fileCopy(dbFile, backup);
                        Logs.error("backup:" + "ok");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logs.error("backup: " + "fail");
                    }
                }
            }

        } else if (command.equals(COMMAND_RESTORE)) {
            if (!dbFile.exists() && backup.exists()) {
                try {
                    fileCopy(backup, dbFile);
                    Logs.error("restore:" + "success");
                } catch (Exception e) {
                    e.printStackTrace();
                    Logs.error("restore:" + "fail");
                }
            }
        } else {
            return null;
        }
        return null;
    }

    public static void fileCopy(File dbFile, File backup) throws IOException {
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    public static boolean restoreDatabase(Context context) {

        boolean re = false;
        File exportDir = new File(Environment.getExternalStorageDirectory()
                .getPath(), "cici");

        File backup = new File(exportDir, DBManager.DB_NAME);
        if (backup.exists()) {
            String dbPath = "/data"
                    + Environment.getDataDirectory().getAbsolutePath() + "/"
                    + context.getPackageName();
            File dbFile = context.getDatabasePath(dbPath + "/"
                    + DBManager.DB_NAME);
            try {
                fileCopy(backup, dbFile);
                Logs.error("restore:" + "success");
                re = true;
            } catch (Exception e) {
                e.printStackTrace();
                Logs.error("restore:" + "fail");
            }
        }
        return re;
    }

}