package com.myth.cici.entity;

import java.io.Serializable;

public class Ci implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    private int id;

    private String author;

    private String text;

    private String note;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }


}
