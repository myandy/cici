package com.myth.cici.activity;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.db.CiDatabaseHelper;
import com.myth.cici.entity.Ci;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.util.ResizeUtil;
import com.myth.cici.wiget.CircleTextView;
import com.myth.cici.wiget.SwitchPoint;

public class CiActivity extends BaseActivity
{

    private ArrayList<Ci> ciList = new ArrayList<Ci>();

    private Cipai cipai;

    private ViewPager gallery;

    SwitchPoint switchPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipai);

        if (getIntent().hasExtra("cipai"))
        {
            cipai = (Cipai) getIntent().getSerializableExtra("cipai");
        }

        ArrayList<Ci> cis = CiDatabaseHelper.getCiByCipaiId(cipai.getId());

        ciList.addAll(cis);
        initView();
    }

    private void initView()
    {
        ColorEntity colorEntity = MyApplication.getColorById(cipai.getColor_id());
        int color = 0xffffff;
        if (colorEntity != null)
        {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        }

        LinearLayout topView = (LinearLayout) findViewById(R.id.top);

        android.widget.LinearLayout.LayoutParams param = new android.widget.LinearLayout.LayoutParams(150, 150);
        topView.addView(new CircleTextView(mActivity, "0" + cipai.getId(), color), param);

        TextView title = (TextView) findViewById(R.id.title);
        title.setTextSize(50);
        title.setText(cipai.getName());

        gallery = (ViewPager) findViewById(R.id.gc_main_gallery);

        switchPoint = (SwitchPoint) findViewById(R.id.gc_main_dot);

        // 释放触摸控制，并且设置点点
        gallery.setOnPageChangeListener(new OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
                gallery.getParent().requestDisallowInterceptTouchEvent(true);
                if (ciList != null && ciList.size() > 0)
                {
                    switchPoint.setSelectedSwitchBtn(arg0 % ciList.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });

        /**
         * 滚图
         */
        if (ciList != null && ciList.size() > 0)
        {
            switchPoint.addSwitchBtn(ciList.size(), R.drawable.gc_cover_switcher_dot, ResizeUtil.resize(mActivity, 10),
                    ResizeUtil.resize(mActivity, 10));
            switchPoint.setSelectedSwitchBtn(0);
            gallery.setCurrentItem(0);
            gallery.setAdapter(galleryAdapter);
            galleryAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 滚图的adapter
     */
    private PagerAdapter galleryAdapter = new PagerAdapter()
    {
        public Object instantiateItem(android.view.ViewGroup container, int position)
        {
            View root = getLayoutInflater().inflate(R.layout.layout_textview, null);

            LayoutParams param = new LayoutParams(100, 100);
            // TextView textView = new TextView(mActivity);
            // container.addView(textView, param);

            container.addView(root, param);
            TextView textView = (TextView) root.findViewById(R.id.textview);
            textView.setText(ciList.get(position % ciList.size()).getText() + "2222222");

            return root;
        };

        public void destroyItem(android.view.ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        };

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object)
        {
            return super.getItemPosition(object) % ciList.size();
        }

        @Override
        public int getCount()
        {
            if (ciList == null)
            {
                return 0;
            }
            return Integer.MAX_VALUE;
        }
    };

}
