package com.myth.cici.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Cipai;
import com.myth.cici.wiget.CipaiItem;

public class CipaiListActivity extends BaseActivity
{

    private HorizontalScrollView scrollView;

    private LinearLayout linearLayout;

    private ArrayList<Cipai> ciList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cipai_list);
        ciList = CipaiDatabaseHelper.getAllCipai();

        if (ciList == null || ciList.size() == 0)
        {
            finish();
        }
        initView();
    }

    private void initView()
    {
        scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        int length = ciList.size() % 2 == 0 ? ciList.size() / 2 : ciList.size() / 2 + 1;
        for (int i = 0; i < length; i++)
        {
            if (i == 0 && ciList.size() % 2 != 0)
            {
                linearLayout.addView(new CipaiItem(mActivity, ciList.get(0), null));
            }
            else
            {
                linearLayout.addView(new CipaiItem(mActivity, ciList.get(2 * i), ciList.get(2 * i + 1)));
            }
        }
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 50);

    }

    public static void scrollToBottom(final View scroll, final View inner)
    {

        Handler mHandler = new Handler();

        mHandler.post(new Runnable()
        {
            public void run()
            {
                if (scroll == null || inner == null)
                {
                    return;
                }
                int offset = inner.getMeasuredHeight() - scroll.getHeight();
                if (offset < 0)
                {
                    offset = 0;
                }

                scroll.scrollTo(0, offset);
            }
        });
    }

}
