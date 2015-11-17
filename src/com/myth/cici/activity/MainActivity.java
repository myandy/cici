package com.myth.cici.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.db.BackupTask;
import com.myth.cici.db.WritingDatabaseHelper;
import com.myth.cici.entity.Writing;
import com.myth.cici.util.DisplayUtil;
import com.myth.cici.util.ResizeUtil;
import com.myth.cici.wiget.IntroductionView;
import com.myth.cici.wiget.MainView;
import com.myth.cici.wiget.TouchEffectImageView;
import com.myth.cici.wiget.WritingView;

/**
 * ViewPager实现画廊效果
 * 
 * @author Trinea 2013-04-03
 */
public class MainActivity extends BaseActivity
{

    private RelativeLayout viewPagerContainer;

    private ViewPager viewPager;

    private ArrayList<Writing> writings;

    private MyPagerAdapter pagerAdapter;

    private int currentpage = 0;

    private boolean firstIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        layoutItemContainer(viewPager);
        viewPagerContainer = (RelativeLayout) findViewById(R.id.pager_layout);

        viewPager.setOffscreenPageLimit(3);
        pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);

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

        getBottomLeftView().setImageResource(R.drawable.add);
        getBottomLeftView().setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mActivity, CipaiSearchActivity.class);
                startActivity(intent);
            }
        });
        ImageView setting = new TouchEffectImageView(mActivity, null);
        setting.setImageResource(R.drawable.setting);
        setting.setScaleType(ScaleType.FIT_XY);
        addBottomRightView(setting,
                new LayoutParams(DisplayUtil.dip2px(mActivity, 48), DisplayUtil.dip2px(mActivity, 48)));
        setting.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mActivity, SettingActivity.class);
                startActivity(intent);
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
        refresh();
    }
    
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

        new BackupTask(this).execute(BackupTask.COMMAND_BACKUP);
    }

    public void refresh()
    {
        writings = WritingDatabaseHelper.getAllWriting(mActivity);
        pagerAdapter.setWritings(writings);
        pagerAdapter.notifyDataSetChanged();

        if (firstIn)
        {
            currentpage = pagerAdapter.getCount() - 1;
            firstIn = false;
        }
        if (currentpage > pagerAdapter.getCount() - 1)
        {
            currentpage = pagerAdapter.getCount() - 1;
        }
        viewPager.setCurrentItem(currentpage);
    }

    /**
     * this is a example fragment, just a imageview, u can replace it with
     * your needs
     * 
     * @author Trinea 2013-04-03
     */
    class MyPagerAdapter extends PagerAdapter
    {

        private ArrayList<Writing> datas;

        @Override
        public int getCount()
        {
            if (datas == null || datas.size() == 0)
            {
                return 2;
            }
            return datas.size() + 1;
        }

        public boolean isNoWriting()
        {
            return (datas == null || datas.isEmpty());
        }

        public ArrayList<Writing> getWritings()
        {
            return datas;
        }

        public void setWritings(ArrayList<Writing> writings)
        {
            if (datas == null)
            {
                datas = new ArrayList<Writing>();
            }
            datas.clear();
            datas.addAll(writings);
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
            if (position == getCount() - 1)
            {
                view = new MainView(mActivity);
            }
            else if (isNoWriting())
            {
                view = new IntroductionView(mActivity);
            }
            else
            {
                view = new WritingView(mActivity, datas.get(position));
            }
            ((ViewPager) container).addView(view, 0);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

    }

    public class MyOnPageChangeListener implements OnPageChangeListener
    {

        @Override
        public void onPageSelected(int position)
        {
            currentpage = position;
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
