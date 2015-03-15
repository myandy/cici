package com.myth.cici.wiget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.activity.CiActivity;
import com.myth.cici.activity.CipaiListActivity;

public class MainView extends RelativeLayout
{


    private Context mContext;

    public MainView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public MainView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MainView(Context context)
    {
        super(context);
        mContext = context;
        initView();
    }

    private void initView()
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_main, null);

        ViewGroup showAll = (ViewGroup) root.findViewById(R.id.show_all);
        showAll.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                mContext.startActivity(new Intent(mContext, CipaiListActivity.class));
            }
        });
        for (int i = 0; i < showAll.getChildCount(); i++)
        {
            ((TextView) showAll.getChildAt(i)).setTypeface(MyApplication.typeface);
        }

        TextView showOne = (TextView) root.findViewById(R.id.show_one);
        showOne.setTypeface(MyApplication.typeface);
        showOne.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, CiActivity.class);
                mContext.startActivity(intent);

            }
        });

        addView(root, new LayoutParams(-1, -1));
    }

}
