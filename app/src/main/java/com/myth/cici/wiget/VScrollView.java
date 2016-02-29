package com.myth.cici.wiget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 反弹效果ScrollView http://blog.csdn.net/freesonhp/article/details/9001037
 * 
 * @author likebamboo
 */
public class VScrollView extends ScrollView
{

    Context mContext;

    private View mView;

    private float touchY;

    private int scrollY = 0;

    private boolean handleStop = false;

    private int eachStep = 0;

    private static final int MAX_SCROLL_HEIGHT = 200;// 最大滑动距离

    private static final float SCROLL_RATIO = 0.4f;// 阻尼系数,越小阻力就越大

    public VScrollView(Context context)
    {
        super(context);
        this.mContext = context;
    }

    public VScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
    }

    public VScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate()
    {
        if (getChildCount() > 0)
        {
            this.mView = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0)
    {
        if (arg0.getAction() == MotionEvent.ACTION_DOWN)
        {
            touchY = arg0.getY();
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (mView == null)
        {
            return super.onTouchEvent(ev);
        }
        else
        {
            commonOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void commonOnTouchEvent(MotionEvent ev)
    {
        int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                if (mView.getScrollY() != 0)
                {
                    handleStop = true;
                    animation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                int deltaY = (int) (touchY - nowY);
                touchY = nowY;
                if (isNeedMove())
                {
                    int offset = mView.getScrollY();
                    if (offset < MAX_SCROLL_HEIGHT && offset > -MAX_SCROLL_HEIGHT)
                    {
                        mView.scrollBy(0, (int) (deltaY * SCROLL_RATIO));
                        handleStop = false;
                    }
                }

                break;
            default:
                break;
        }
    }

    private boolean isNeedMove()
    {
        int viewHight = mView.getMeasuredHeight();
        int srollHight = getHeight();
        int offset = viewHight - srollHight;
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset)
        {
            return true;
        }
        return false;
    }

    private void animation()
    {
        scrollY = mView.getScrollY();
        eachStep = scrollY / 10;
        /**
         * fix 解决当下拉或上拉距离非常小的时候不能回到原点的bug
         */
        if (eachStep == 0)
        {
            if (scrollY < 0)
            {
                eachStep = -1;
            }
            else
            {
                eachStep = 1;
            }
        }
        // fix end
        resetPositionHandler.sendEmptyMessage(0);
    }

    @SuppressLint("HandlerLeak")
    Handler resetPositionHandler = new Handler()
    {
        @Override
        public void handleMessage(android.os.Message msg)
        {
            if (scrollY != 0 && handleStop)
            {
                scrollY -= eachStep;
                if ((eachStep < 0 && scrollY > 0) || (eachStep > 0 && scrollY < 0))
                {
                    scrollY = 0;
                }
                mView.scrollTo(0, scrollY);
                sendEmptyMessageDelayed(0, 5);
            }
        };
    };
}