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

    private static final int TYPE_PLUS1 = 40;

    private static String[] typeString = {"平韵", "仄韵", "平仄错叶格", "平仄错叶格1"};

    private int mType;

    private int mColor;

    public static String getYunString(int type)
    {
        if (type == TYPE_CIRCLE)
        {
            return typeString[0];
        }
        else if (type == TYPE_RING)
        {
            return typeString[1];
        }
        else if (type == TYPE_PLUS)
        {
            return typeString[2];
        }
        else if (type == TYPE_PLUS1)
        {
            return typeString[3];
        }
        else
        {
            return "";
        }
    }

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
            canvas.drawCircle(30, 30, 26, paint);
        }
        else if (mType == TYPE_RING)
        {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(6);
            canvas.drawCircle(30, 30, 22, paint);
        }
        else if (mType == TYPE_PLUS)
        {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(27, 4, 33, 56, paint);
            canvas.drawRect(4, 27, 56, 33, paint);
        }
        else if (mType == TYPE_PLUS1)
        {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(27, 4, 33, 56, paint);
            canvas.drawRect(4, 27, 56, 33, paint);
        }
    }

}
