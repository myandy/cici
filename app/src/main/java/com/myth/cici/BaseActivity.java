package com.myth.cici;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity
{

    protected Activity mActivity;

    /**
     * 内容区
     */
    protected FrameLayout mContentLayout = null;

    /**
     * 底部区
     */
    protected FrameLayout mBottomLayout = null;

    private static int statusBarHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        if (VERSION.SDK_INT >= 19)
        {
            // 透明状态栏
            getWindow().addFlags(0x4000000);
            // 透明导航栏
//            getWindow().addFlags(0x8000000);

            if (statusBarHeight == 0)
            {
                statusBarHeight = getStatusBarHeight(this);
            }

        }
        mActivity = this;
        mBottomLayout = (FrameLayout) findViewById(R.id.bottom_layout);
        findViewById(R.id.bottom_left).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        mContentLayout = (FrameLayout) findViewById(R.id.content_layout);
    }

    public static int getStatusBarHeight(Context context)
    {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try
        {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println(statusBarHeight);
        MobclickAgent.onResume(this);
    }

    protected ImageView getBottomLeftView()
    {
        return (ImageView) findViewById(R.id.bottom_left);
    }

    public void setBottomGone()
    {
        mBottomLayout.setVisibility(View.GONE);
    }

    public void setBottomVisible()
    {
        mBottomLayout.setVisibility(View.VISIBLE);
    }

    protected void addBottomRightView(View view, LayoutParams lps)
    {
        ((ViewGroup) findViewById(R.id.bottom_right)).addView(view, lps);
    }

    protected void addBottomCenterView(View view, LayoutParams lps)
    {
        ((ViewGroup) findViewById(R.id.bottom_center)).addView(view, lps);
    }

    protected void addBottomCenterView(View view)
    {
        ((ViewGroup) findViewById(R.id.bottom_center)).addView(view);
    }

    /**
     * 通过layout名称构建视图
     * 
     * @param layoutResName
     * @see [类、类#方法、类#成员]
     */
    public void setContentView(int layoutId)
    {
        getLayoutInflater().inflate(layoutId, mContentLayout);
        if (VERSION.SDK_INT >= 19)
        {
            mContentLayout.setPadding(0, statusBarHeight, 0, 0);
        }
    }

}
