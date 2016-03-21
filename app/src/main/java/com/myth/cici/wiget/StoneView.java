package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.cici.util.DisplayUtil;

public class StoneView extends View {

    private static final int TYPE_CIRCLE = 10;

    private static final int TYPE_RING = 20;

    private static final int TYPE_PLUS = 30;

    private static final int TYPE_PLUS1 = 40;

    private static String[] typeString = {"平韵", "仄韵", "平仄错叶格", "平仄通韵格"};

    private int mType;

    private int mColor;

    private Context mContext;

    public static String getYunString(int type) {
        if (type == TYPE_CIRCLE) {
            return typeString[0];
        } else if (type == TYPE_RING) {
            return typeString[1];
        } else if (type == TYPE_PLUS) {
            return typeString[2];
        } else if (type == TYPE_PLUS1) {
            return typeString[3];
        } else {
            return "";
        }
    }

    private Paint paint;

    public StoneView(Context context, int type, int color) {
        super(context);
        mType = type;
        mColor = color;
        mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public StoneView(Context context) {
        super(context);
        mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setType(int type, int color) {
        mType = type;
        mColor = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(mColor);
        if (mType == TYPE_CIRCLE) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(DisplayUtil.dip2px(mContext, 12), DisplayUtil.dip2px(mContext, 12),
                    DisplayUtil.dip2px(mContext, 10), paint);
        } else if (mType == TYPE_RING) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(DisplayUtil.dip2px(mContext, 2));
            canvas.drawCircle(DisplayUtil.dip2px(mContext, 12), DisplayUtil.dip2px(mContext, 12),
                    DisplayUtil.dip2px(mContext, 9), paint);
        } else if (mType == TYPE_PLUS) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(DisplayUtil.dip2px(mContext, 11), DisplayUtil.dip2px(mContext, 2),
                    DisplayUtil.dip2px(mContext, 13), DisplayUtil.dip2px(mContext, 22), paint);
            canvas.drawRect(DisplayUtil.dip2px(mContext, 2), DisplayUtil.dip2px(mContext, 11),
                    DisplayUtil.dip2px(mContext, 22), DisplayUtil.dip2px(mContext, 13), paint);
        } else if (mType == TYPE_PLUS1) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(DisplayUtil.dip2px(mContext, 11), DisplayUtil.dip2px(mContext, 2),
                    DisplayUtil.dip2px(mContext, 13), DisplayUtil.dip2px(mContext, 22), paint);
            canvas.drawRect(DisplayUtil.dip2px(mContext, 2), DisplayUtil.dip2px(mContext, 11),
                    DisplayUtil.dip2px(mContext, 22), DisplayUtil.dip2px(mContext, 13), paint);
        }
    }

}
