package com.myth.cici.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.adapter.CipaiListAdapter;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Cipai;

public class CipaiListActivity extends BaseActivity
{

    private RecyclerView listview;

    private ArrayList<Cipai> ciList;

    private boolean isDefault = true;

    private CipaiListAdapter adapter;

    TextView rectLeft;

    TextView rectRight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipai_list);
        isDefault = MyApplication.getDefaulListType(mActivity);
        initView();
    }

    private void initView()
    {
        listview = (RecyclerView) findViewById(R.id.listview);

        listview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listview.setLayoutManager(linearLayoutManager);

        adapter = new CipaiListAdapter(mActivity);
        listview.setAdapter(adapter);
        rectLeft = (TextView) findViewById(R.id.rect_left);
        rectLeft.setTypeface(MyApplication.getTypeface());
        rectRight = (TextView) findViewById(R.id.rect_right);
        rectRight.setTypeface(MyApplication.getTypeface());

        setBackground();

        rectLeft.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                if (!isDefault)
                {
                    isDefault = true;
                    MyApplication.setDefaultListType(mActivity, true);
                    setBackground();
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
                    MyApplication.setDefaultListType(mActivity, false);
                    setBackground();
                    addView();
                }
            }
        });
        addView();
        try
        {
            listview.smoothScrollToPosition(ciList.size());
        }
        catch (Exception e)
        {
            Log.e("CipaiList", e.toString());
        }
    }

    private void addView()
    {
        if (ciList == null || ciList.size() == 0)
        {
            finish();
        }
        adapter.setList(ciList);
        adapter.notifyDataSetChanged();
    }

    private void setBackground()
    {
        if (isDefault)
        {
            rectLeft.setBackgroundResource(R.drawable.rect_left_selected);
            rectLeft.setTextColor(getResources().getColor(R.color.black));
            rectRight.setBackgroundResource(R.drawable.rect_right);
            rectRight.setTextColor(getResources().getColor(R.color.white));
            ciList = CipaiDatabaseHelper.getAllShowCipai();
        }
        else
        {
            rectLeft.setBackgroundResource(R.drawable.rect_left);
            rectLeft.setTextColor(getResources().getColor(R.color.white));
            rectRight.setBackgroundResource(R.drawable.rect_right_selected);
            rectRight.setTextColor(getResources().getColor(R.color.black));
            ciList = CipaiDatabaseHelper.getAllCipaiByWordCount();
        }
    }

}
