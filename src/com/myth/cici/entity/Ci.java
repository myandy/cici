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

    private int ci_id;

    private Cipai cipai;

    public Cipai getCipai()
    {
        return cipai;
    }

    public void setCipai(Cipai cipai)
    {
        this.cipai = cipai;
    }

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

    public int getCi_id()
    {
        return ci_id;
    }

    public void setCi_id(int ci_id)
    {
        this.ci_id = ci_id;
    }

}
