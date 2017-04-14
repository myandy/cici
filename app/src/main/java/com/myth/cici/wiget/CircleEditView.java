package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.cici.R;
import com.myth.cici.util.DisplayUtil;

public class CircleEditView extends View
{

    private int mColor;

    private Context mContext;

    public int getColor()
    {
        return mColor;
    }

    public void setColor(int mColor)
    {
        this.mColor = mColor;
        invalidate();
    }

    public CircleEditView(Context context)
    {
        super(context);
        mContext = context;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(DisplayUtil.dip2px(mContext, 48), DisplayUtil.dip2px(mContext, 48),
                DisplayUtil.dip2px(mContext, 46), paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.edit_white),
                DisplayUtil.dip2px(mContext, 16), DisplayUtil.dip2px(mContext, 16), paint);
    }

}
