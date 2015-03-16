package com.myth.cici.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileUtils
{

    /** 根目录 */
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + "/cici";
    
    /**
     * 保存文件
     * 
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm) throws IOException
    {
        File root = new File(ROOT_DIR);
        if (!root.exists())
        {
            root.mkdir();
        }
        String fileName = MD5.MD5_16(Bitmap2Bytes(bm));
        File myCaptureFile = new File(ROOT_DIR + "/" + fileName + ".jpg");
        if (!myCaptureFile.exists())
        {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        }
        return myCaptureFile.getAbsolutePath();

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
        File root = new File(ROOT_DIR);
        if (!root.exists())
        {
            root.mkdir();
        }
        File myCaptureFile = new File(ROOT_DIR + "/" + fileName + ".jpg");
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
