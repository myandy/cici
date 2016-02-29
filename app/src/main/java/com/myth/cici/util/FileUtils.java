package com.myth.cici.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileUtils
{

    /** 根目录 */
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + "/cici";

    /** 背景目录 */
    public static final String BACKGROUND_DIR = ROOT_DIR + "/background";

    /** 分享目录 */
    public static final String SHARE_DIR = ROOT_DIR + "/share";

    /**
     * 保存文件
     * 
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm) throws IOException
    {
        createDir(ROOT_DIR);
        createDir(BACKGROUND_DIR);
        String fileName =bm.hashCode()+"";
        File myCaptureFile = new File(BACKGROUND_DIR + "/" + fileName + ".jpg");
        if (!myCaptureFile.exists())
        {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        }
        return myCaptureFile.getAbsolutePath();

    }

    private static void createDir(String dir)
    {
        File root = new File(dir);
        if (!root.exists())
        {
            root.mkdir();
        }
    }

    /**
     * 保存文件
     * 
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String fileName) throws IOException
    {
        createDir(ROOT_DIR);
        createDir(SHARE_DIR);
        File myCaptureFile = new File(SHARE_DIR + "/" + fileName + ".jpg");
        if (!myCaptureFile.exists())
        {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        }
        return myCaptureFile.getAbsolutePath();
    }

    public static byte[] Bitmap2Bytes(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap Bytes2Bimap(byte[] b)
    {
        if (b.length != 0)
        {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        else
        {
            return null;
        }
    }
    
}
