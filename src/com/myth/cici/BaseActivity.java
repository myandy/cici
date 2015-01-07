package com.myth.cici;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

public class BaseActivity extends Activity
{

    protected Activity mActivity;



    /**
     * 内容区
     */
    protected FrameLayout mContentLayout = null;

    /**
     * loading区
     */
    protected View mProgressBar = null;

    protected View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = this;
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

}
