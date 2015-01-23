package com.myth.cici;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity
{

    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

}
