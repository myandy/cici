package com.myth.cici;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
    
    protected void setBottomGone()
    {
        mBottomLayout.setVisibility(View.GONE);
    }

    protected void addBottomRightView(View view) {
		((ViewGroup)findViewById(R.id.bottom_right)).addView(view);
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
