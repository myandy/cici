package com.myth.cici.wiget;

import android.content.Context;
import android.util.AttributeSet;

import com.myth.cici.R;

public class SelectImageView extends TouchEffectImageView
{



    public SelectImageView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
    }

    public void setSelect(boolean select)
    {
        if (select)
        {
            setBackgroundResource(R.drawable.circle_select);
        }
        else
        {
            setBackgroundDrawable(null);
        }
    }

}
