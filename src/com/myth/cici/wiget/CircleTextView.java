package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.cici.MyApplication;
import com.myth.cici.R;

public class CircleTextView extends View
{

    private int mColor;

    private String mText;

    public CircleTextView(Context context, String text, int color)
    {
        super(context);
        mColor = color;
        mText = text;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(80, 80, 76, paint);
        paint.setColor(R.color.white);
        paint.setTextSize(44);
        paint.setTypeface(MyApplication.typeface);
        canvas.drawText(mText, 53, 93, paint);
    }

}
