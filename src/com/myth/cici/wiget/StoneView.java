package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class StoneView extends View
{

    private static final int TYPE_CIRCLE = 10;

    private static final int TYPE_RING = 20;

    private static final int TYPE_PLUS = 30;

    private int mType;

    private int mColor;

    public StoneView(Context context, int type, int color)
    {
        super(context);
        mType = type;
        mColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        if (mType == TYPE_CIRCLE)
        {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(40, 40, 38, paint);
        }
        else if (mType == TYPE_RING)
        {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(16);
            canvas.drawCircle(40, 40, 30, paint);
        }
        else if (mType == TYPE_PLUS)
        {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(35, 4, 45, 76, paint);
            canvas.drawRect(4, 35, 76, 45, paint);
        }
    }

}
