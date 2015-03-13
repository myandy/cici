package com.myth.cici.entity;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Writing implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    private int id;

    private String text;

    private int ci_id;

    private long create_dt;

    private long update_dt;

    private String bgimg;

    private int layout;

    private Bitmap bitmap;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }


    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }


    public long getCreate_dt()
    {
        return create_dt;
    }

    public void setCreate_dt(long create_dt)
    {
        this.create_dt = create_dt;
    }

    public long getUpdate_dt()
    {
        return update_dt;
    }

    public void setUpdate_dt(long update_dt)
    {
        this.update_dt = update_dt;
    }

    public String getBgimg()
    {
        return bgimg;
    }

    public void setBgimg(String bgimg)
    {
        this.bgimg = bgimg;
    }

    public int getLayout()
    {
        return layout;
    }

    public void setLayout(int layout)
    {
        this.layout = layout;
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    public int getCi_id()
    {
        return ci_id;
    }

    public void setCi_id(int ci_id)
    {
        this.ci_id = ci_id;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

}
