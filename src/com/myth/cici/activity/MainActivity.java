package com.myth.cici.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.db.WritingDatabaseHelper;
import com.myth.cici.entity.Writing;
import com.myth.cici.util.ResizeUtil;
import com.myth.cici.wiget.IntroductionView;
import com.myth.cici.wiget.MainView;

/**
 * ViewPager实现画廊效果
 * 
 * @author Trinea 2013-04-03
 */
public class MainActivity extends BaseActivity
{

    private int count = 3;

    private RelativeLayout viewPagerContainer;

    private ViewPager viewPager;

    private ArrayList<Writing> writings;

    private boolean noWriting = true;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        layoutItemContainer(viewPager);
        viewPagerContainer = (RelativeLayout) findViewById(R.id.pager_layout);

        pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(count);

        // to cache all page, or we will see the right item delayed

        viewPager.setPageMargin(ResizeUtil.resize(mActivity, 60));
        MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
        viewPager.setOnPageChangeListener(myOnPageChangeListener);

        viewPagerContainer.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // dispatch the events to the ViewPager, to solve the problem
                // that we can swipe only the middle view.
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }

    private void layoutItemContainer(View itemContainer)
    {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) itemContainer.getLayoutParams();
        params.width = ResizeUtil.resize(mActivity, 540);
        params.height = LayoutParams.MATCH_PARENT;
        itemContainer.setLayoutParams(params);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        writings = WritingDatabaseHelper.getAllWriting(mActivity);
        if (writings.size() == 0)
        {
            noWriting = true;
            count = 2;
        }
        else
        {
            noWriting = false;
            count = writings.size() + 1;
        }
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(count - 1);
    }

    /**
     * this is a example fragment, just a imageview, u can replace it with
     * your needs
     * 
     * @author Trinea 2013-04-03
     */
    class MyPagerAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view;
            if (position == count - 1)
            {
                view = new MainView(mActivity);
            }
            else if (position == 1)
            {
                view = new IntroductionView(mActivity);
            }
            else
            {
                ImageView imageView = new ImageView(mActivity);
                imageView.setImageResource(R.drawable.bg001);
                imageView.setScaleType(ScaleType.FIT_XY);
                view = imageView;
            }
            ((ViewPager) container).addView(view, 0);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener
    {

        @Override
        public void onPageSelected(int position)
        {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            // to refresh frameLayout
            if (viewPagerContainer != null)
            {
                viewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {
        }
    }
}
