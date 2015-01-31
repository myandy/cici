package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.cici.R;

public class CircleEditView extends View
{

    private int mColor;


    public CircleEditView(Context context, int color)
    {
        super(context);
        mColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(mColor);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(120, 120, 116, paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.edit_white), 40, 40, paint);
    }

}
