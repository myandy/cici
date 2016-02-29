/**
 * (c) 2012 PPLive Inc.
 * 
 * @author: Danny Sun <dannysun@pplive.com>
 * @date: 2012-02-28
 */
package com.myth.cici.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 提供与日期有关的工具方法
 */
public class DateUtils
{

    private DateUtils()
    {
    }

    /**
     * YYYY-MM-DD时间格式，示例：1986-04-09
     */
    public static final String YMD_FORMAT = "yyyy-MM-dd";

    /**
     * YYYY-MM时间格式，示例：1986-04
     */
    public static final String YM_FORMAT = "yyyy-MM";

    /**
     * MM-DD时间格式，示例：04-09
     */
    public static final String MD_FORMAT = "MM-dd";

    /**
     * MM.DD时间格式，示例：04.09
     */
    public static final String MD_FORMAT_2 = "MM.dd";

    /**
     * YYYY-MM-DD时间格式，示例：1986-04-09
     */
    public static final String HMS_FORMAT = "HH:mm:ss";

    /**
     * HH:mm时间格式，示例：18:30
     */
    public static final String HM_FORMAT = "HH:mm";

    /**
     * YYYY-MM-DD HH:mm:ss时间格式，示例：1986-04-09 18:30:29
     */
    public static final String YMD_HMS_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * YYYY-MM-DD HH:mm时间格式，示例：1986-04-09 18:30
     */
    public static final String YMD_HM_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * M月d日 示例：示例：4月9日
     */
    public static final String MD2_FORMAT = "M月d日";

    /**
     * M月d日 HH:mm时间格式，示例：4月9日 18:30
     */
    public static final String MD_HM_FORMAT = "M月d日 HH:mm";

    /**
     * GMT时间
     */
    public static final String GMT_TIME = "EEE, dd MMM yyyy HH:mm:ss z";

    public static final String GMT_TIME_LOCAL = "EEE MMM dd HH:mm:ss yyyy";

    /**
     * Y年M月d日 HH:mm时间格式，示例：2014年4月9日 18:30
     */
    public static final String YMD2_HM_FORMAT = "yyyy年M月d日 HH:mm";

    /**
     * Converts a string representation of a Date to its Date object.
     * 
     * @param date
     * @param format
     * @return
     */

    public static String longToFormat(long time, String format)
    {
        return dateToString(new Date(time), format);
    }

    public static String dateToString(Date date, String format)
    {
        DateFormat outputDf = null;
        try
        {
            outputDf = new SimpleDateFormat(format);
        }
        catch (Exception e)
        {
            return null;
        }
        return outputDf.format(date);
    }

}
