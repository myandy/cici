package com.myth.cici;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT)
        {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
    }

}
