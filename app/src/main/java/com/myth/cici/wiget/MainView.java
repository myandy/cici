package com.myth.cici.wiget;

import android.app.Activity;
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
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;

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
        MyApplication myApplication = (MyApplication) ((Activity) mContext).getApplication();

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
            ((TextView) showAll.getChildAt(i)).setTypeface(MyApplication.getTypeface());
        }

        TextView showOne = (TextView) root.findViewById(R.id.show_one);
        showOne.setTypeface(MyApplication.getTypeface());
        showOne.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, CiActivity.class);
                mContext.startActivity(intent);

            }
        });

        TextView share = (TextView) root.findViewById(R.id.community);
        share.setTypeface(myApplication.getTypeface());
        share.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 获取CommunitySDK实例, 参数1为Context类型
                CommunitySDK mCommSDK = CommunityFactory.getCommSDK(mContext);
                // 打开微社区的接口, 参数1为Context类型
                mCommSDK.openCommunity(mContext);

            }
        });


        addView(root, new LayoutParams(-1, -1));
    }

}
