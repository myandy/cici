package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;
import android.view.View;

public class StoneView extends View
{

    private static final int TYPE_CIRCLE = 10;

    private static final int TYPE_RING = 20;

    private static final int TYPE_PLUS = 30;

    private int mType;

    public StoneView(Context context, int type)
    {
        super(context);
        mType = type;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();

        if (mType == TYPE_CIRCLE)
        {
            canvas.drawCircle(5, 5, 10, paint);
        }
        else if (mType == TYPE_RING)
        {
            canvas.drawCircle(5, 5, 10, paint);
            canvas.drawCircle(5, 5, 7, paint);
        }
        Shape shape = new Shape()
        {

            @Override
            public void draw(Canvas canvas, Paint paint)
            {
                // TODO Auto-generated method stub

            }
        };
    }

}
