package com.myth.cici.wiget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.activity.CipaiActivity;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.entity.Writing;

public class WritingView extends RelativeLayout
{

    private Context mContext;
    
    private Writing writing;

    public WritingView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public WritingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public WritingView(Context context,Writing writing)
    {
        super(context);
        this.writing=writing;
    }

    public WritingView(Context context, Cipai cipai1, Cipai cipai2)
    {
        super(context);
        mContext = context;
        initView();
    }

    private void initView()
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_writing, null);


        addView(root);

    }


}
