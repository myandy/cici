package com.myth.cici.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.util.ResizeUtil;
import com.myth.cici.wiget.SwitchPoint;

public class GCImageViewActivity extends BaseActivity
{

    /**
     * 图片viewpage
     */
    private ViewPager gallery;

    /**
     * 列表持有实例
     */
    private ArrayList<String> urlList;

    /**
     * 滚图的点点
     */
    private SwitchPoint switchPoint;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra("list"))
        {
            urlList = getIntent().getStringArrayListExtra("list");
            index = getIntent().getIntExtra("index", 0);
        }
        else
        {
            finish();
        }
        setContentView(R.layout.gc_image_view_activity);
        initView();
    }

    private void initView()
    {
        gallery = (ViewPager) findViewById(R.id.gc_image_view_gallery);
        gallery.setAdapter(galleryAdapter);

        switchPoint = (SwitchPoint) findViewById(R.id.gc_image_view_dot);

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
                if (urlList.size() > 0)
                {
                    switchPoint.setSelectedSwitchBtn(arg0 % urlList.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });
        switchPoint.addSwitchBtn(urlList.size(), R.drawable.gc_cover_switcher_dot,
                ResizeUtil.resize(mActivity, 10), ResizeUtil.resize(mActivity, 10));
        switchPoint.setSelectedSwitchBtn(index);
        gallery.setCurrentItem(index);

    }

    /**
     * 滚图的adapter
     */
    private PagerAdapter galleryAdapter = new PagerAdapter()
    {
        int[] ids = {R.drawable.intro00, R.drawable.intro01, R.drawable.intro02, R.drawable.intro03,
                R.drawable.intro04, R.drawable.intro05};

        public Object instantiateItem(android.view.ViewGroup container, int position)
        {
            View root = getLayoutInflater().inflate(R.layout.gc_main_gallery_item, null);
            ImageView imageView = (ImageView) root.findViewById(R.id.gc_gallery_iv);
            imageView.setImageResource(ids[position]);
            container.addView(root);
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
            return super.getItemPosition(object);
        }

        @Override
        public int getCount()
        {
            return ids.length;
        }
    };
}
