//package com.myth.cici.activity;
//
//import java.util.ArrayList;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.HorizontalScrollView;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.TextView;
//
//import com.myth.cici.BaseActivity;
//import com.myth.cici.MyApplication;
//import com.myth.cici.R;
//import com.myth.cici.db.CipaiDatabaseHelper;
//import com.myth.cici.entity.Cipai;
//import com.myth.cici.util.DisplayUtil;
//import com.myth.cici.wiget.CipaiItem;
//
//public class CopyOfCipaiListActivity extends BaseActivity
//{
//
//    private HorizontalScrollView scrollView;
//
//    private LinearLayout linearLayout;
//
//    private ArrayList<Cipai> ciList;
//
//    private ArrayList<Cipai> ciList1;
//
//    private ArrayList<Cipai> ciList2;
//
//    private boolean isDefault = true;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_cipai_list);
//        ciList1 = CipaiDatabaseHelper.getAllShowCipai();
//        ciList2 = CipaiDatabaseHelper.getAllCipaiByWordCount();
//        ciList = ciList1;
//
//        if (ciList == null || ciList.size() == 0)
//        {
//            finish();
//        }
//        initView();
//    }
//
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//    }
//
//    private void initView()
//    {
//        scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
//        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
//        final TextView rectLeft = (TextView) findViewById(R.id.rect_left);
//        rectLeft.setTypeface(MyApplication.typeface);
//        final TextView rectRight = (TextView) findViewById(R.id.rect_right);
//        rectRight.setTypeface(MyApplication.typeface);
//        rectLeft.setOnClickListener(new OnClickListener()
//        {
//
//            @Override
//            public void onClick(View arg0)
//            {
//                if (!isDefault)
//                {
//                    isDefault = true;
//                    rectLeft.setBackgroundResource(R.drawable.rect_left_selected);
//                    rectRight.setBackgroundResource(R.drawable.rect_right);
//                    ciList = ciList1;
//                    addView();
//                }
//            }
//        });
//        rectRight.setOnClickListener(new OnClickListener()
//        {
//
//            @Override
//            public void onClick(View arg0)
//            {
//                if (isDefault)
//                {
//                    isDefault = false;
//                    rectLeft.setBackgroundResource(R.drawable.rect_left);
//                    rectRight.setBackgroundResource(R.drawable.rect_right_selected);
//                    ciList = ciList2;
//                    addView();
//                }
//            }
//        });
//        addView();
//    }
//
//    private void addView()
//    {
//        linearLayout.removeAllViews();
//        int length = ciList.size() / 2;
//        for (int i = 0; i < length; i++)
//        {
//            LayoutParams params = new LayoutParams(DisplayUtil.dip2px(mActivity, 70), -1);
//            params.setMargins(DisplayUtil.dip2px(mActivity, 12), 0, 0, 0);
//            linearLayout.addView(new CipaiItem(mActivity, ciList.get(2 * i), ciList.get(2 * i + 1)), params);
//        }
//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
//            }
//        }, 5);
//    }
//
// }
