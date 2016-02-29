package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class TouchEffectImageView extends ImageView
{

    private boolean clickAble = true;

    public TouchEffectImageView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if (clickAble)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    Drawable drawable = getDrawable();
                    if (drawable != null)
                    {
                        drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    Drawable drawableUp = getDrawable();
                    if (drawableUp != null)
                    {
                        drawableUp.mutate().clearColorFilter();
                    }
                    break;
            }
        }

        return super.onTouchEvent(event);
    }

    public void setClickDisable()
    {
        clickAble = false;
        Drawable drawable = getDrawable();
        if (drawable != null)
        {
            drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }
    }

    public void setClickEnable()
    {
        clickAble = true;
        Drawable drawableUp = getDrawable();
        if (drawableUp != null)
        {
            drawableUp.mutate().clearColorFilter();
        }
    }
}
