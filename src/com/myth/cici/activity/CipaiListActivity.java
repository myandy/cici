package com.myth.cici.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.adapter.CipaiListAdapter;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Cipai;
import com.myth.cici.wiget.HorizontalListView;

public class CipaiListActivity extends BaseActivity
{

    private HorizontalListView listview;

    private ArrayList<Cipai> ciList;

    private boolean isDefault = true;

    private CipaiListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cipai_list);
        ciList = CipaiDatabaseHelper.getAllShowCipai();

        if (ciList == null || ciList.size() == 0)
        {
            finish();
        }
        initView();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }

    private void initView()
    {
        listview = (HorizontalListView) findViewById(R.id.listview);
        adapter = new CipaiListAdapter(mActivity);
        listview.setAdapter(adapter);
        final TextView rectLeft = (TextView) findViewById(R.id.rect_left);
        rectLeft.setTypeface(MyApplication.typeface);
        final TextView rectRight = (TextView) findViewById(R.id.rect_right);
        rectRight.setTypeface(MyApplication.typeface);
        rectLeft.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                if (!isDefault)
                {
                    isDefault = true;
                    rectLeft.setBackgroundResource(R.drawable.rect_left_selected);
                    rectRight.setBackgroundResource(R.drawable.rect_right);
                    ciList = CipaiDatabaseHelper.getAllShowCipai();
                    addView();
                }
            }
        });
        rectRight.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                if (isDefault)
                {
                    isDefault = false;
                    rectLeft.setBackgroundResource(R.drawable.rect_left);
                    rectRight.setBackgroundResource(R.drawable.rect_right_selected);
                    ciList = CipaiDatabaseHelper.getAllCipaiByWordCount();
                    addView();
                }
            }
        });
        addView();

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                listview.scrollToRight();

            }
        }, 50);
        // listview.scrollToRight();
    }

    private void addView()
    {
        adapter.setList(ciList);
        adapter.notifyDataSetChanged();
    }

}
