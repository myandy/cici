package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.cici.R;

public class PingzeView extends View
{

    private static final int TYPE_PING = 1;

    private static final int TYPE_ZHONG = 2;

    private static final int TYPE_ZE = 3;

    private static final int TYPE_PING_YUN = 4;

    private static final int TYPE_ZE_YUN = 6;

    private int mType;

    private Context mContext;

    public PingzeView(Context context, int type)
    {
        super(context);
        mType = type;
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();


        if (mType == TYPE_PING)
        {
            paint.setColor(mContext.getResources().getColor(R.color.yun_white));
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(15, 15, 12, paint);
        }
        else if (mType == TYPE_ZE)
        {
            paint.setColor(mContext.getResources().getColor(R.color.black));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(15, 15, 12, paint);
        }
        else if (mType == TYPE_ZHONG)
        {
            paint.setColor(mContext.getResources().getColor(R.color.yun_white));
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(15, 15, 5, paint);
            canvas.drawCircle(15, 15, 15, paint);
        }
        else if (mType == TYPE_ZE_YUN)
        {
            paint.setColor(mContext.getResources().getColor(R.color.red));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(15, 15, 12, paint);
        }
        else if (mType == TYPE_PING_YUN)
        {
            paint.setColor(mContext.getResources().getColor(R.color.blue));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(15, 15, 12, paint);
        }
    }

}
