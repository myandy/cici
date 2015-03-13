package com.myth.cici.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

/**
 * 这里MD5_16取了MD5_32的中间16位
 */
public class MD5
{

    private static String byte2Hex(byte b)
    {
        int value = (b & 0x7F) + (b < 0 ? 0x80 : 0);
        return (value < 0x10 ? "0" : "") + Integer.toHexString(value).toLowerCase();
    }

    public static String MD5_32(String passwd)
    {
        if (TextUtils.isEmpty(passwd))
        {
            return null;
        }
        try
        {
            return MD5_32(passwd.getBytes(HTTP.UTF_8));
        }
        catch (UnsupportedEncodingException e)
        {
            Logs.error(e.toString());
        }
        return null;
    }

    public static String MD5_32(byte[] passwd)
    {

        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            StringBuffer strbuf = new StringBuffer();

            // md5.update(passwd.getBytes(), 0, passwd.length());
            md5.update(passwd);
            byte[] digest = md5.digest();

            for (int i = 0; i < digest.length; i++)
            {
                strbuf.append(byte2Hex(digest[i]));
            }

            return strbuf.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            Logs.error(e.toString());
        }

        return null;
    }

    public static String MD5_16(String passwd)
    {
        return MD5_32(passwd).subSequence(8, 24).toString();
    }

    public static String MD5_16(byte[] passwd)
    {
        return MD5_32(passwd).subSequence(8, 24).toString();
    }
    
    /**
     * 对文件本身进行md5加密
     * 
     * @param file
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String MD5_32(File file)
    {
        FileInputStream in = null;
        FileChannel ch = null;
        try
        {
            if(file == null)
            {
                return null;
            }
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messageDigest.update(byteBuffer);
            StringBuffer strbuf = new StringBuffer();
            byte[] digest = messageDigest.digest();

            for (int i = 0; i < digest.length; i++)
            {
                strbuf.append(byte2Hex(digest[i]));
            }

            return strbuf.toString();
        }
        catch (Exception e)
        {
            Logs.error(e + "");
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
                if (ch != null)
                {
                    ch.close();
                }
            }
            catch (Exception e)
            {
                Logs.error(e + "");
            }
        }
        return null;
    }
}
