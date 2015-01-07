package com.myth.cici.util;

import android.util.Log;

/**
 * 日志工具
 * 
 * @author zachwang
 */
public class Logs
{

    /**
     * 异常栈位移
     */
    private static final int EXCEPTION_STACK_INDEX = 2;

    public static boolean isDebuggable()
    {
        // return false;
        return true;
    }

    /**
     * verbose级别的日志
     * 
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void verbose(String msg)
    {

        if (isDebuggable())
        {
            Log.v(getTag(), msg);
        }
    }

    /**
     * debug级别的日志
     * 
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void debug(String msg)
    {
        if (isDebuggable())
        {
            Log.d(getTag(), msg);
        }
    }

    /**
     * info级别的日志
     * 
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void info(String msg)
    {
        if (isDebuggable())
        {
            Log.i(getTag(), msg);
        }
    }

    /**
     * warn级别的日志
     * 
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void warn(String msg)
    {
        if (isDebuggable())
        {
            Log.w(getTag(), msg);
        }
    }

    /**
     * error级别的日志
     * 
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void error(String msg)
    {
        if (isDebuggable())
        {
            String tag = getTag();
            Log.e(tag, msg);
        }
    }

    /**
     * 获取日志的标签 格式：类名_方法名_行号 （需要权限：android.permission.GET_TASKS）
     * 
     * @return tag
     * @see [类、类#方法、类#成员]
     */
    private static String getTag()
    {
        try
        {
            Exception exception = new LogException();
            if (exception.getStackTrace() == null || exception.getStackTrace().length <= EXCEPTION_STACK_INDEX)
            {
                return "***";
            }
            StackTraceElement element = exception.getStackTrace()[EXCEPTION_STACK_INDEX];

            String className = element.getClassName();

            int index = className.lastIndexOf(".");
            if (index > 0)
            {
                className = className.substring(index + 1);
            }

            return className + "_" + element.getMethodName() + "_" + element.getLineNumber();

        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return "***";
        }
    }

    /**
     * 取日志标签用的的异常类，只是用于取得日志标签
     */
    private static class LogException extends Exception
    {
        /**
         * 注释内容
         */
        private static final long serialVersionUID = 1L;
    }
}
