package com.myth.cici.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.myth.cici.R;
import com.myth.cici.entity.Cipai;

public class CipaiItem extends RelativeLayout
{

    private Cipai cipai1;

    private Cipai cipai2;

    public CipaiItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public CipaiItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CipaiItem(Context context)
    {
        super(context);
    }

    public CipaiItem(Context context, Cipai cipai1, Cipai cipai2)
    {
        super(context);
        this.cipai1 = cipai1;
        this.cipai2 = cipai2;
        initView(context);
    }

    private void initView(final Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_cipai_item, null);

        View head1 = root.findViewById(R.id.head1);
        ViewGroup middle1 = (ViewGroup) root.findViewById(R.id.middle1);
        VerticalTextView num_1 = (VerticalTextView) root.findViewById(R.id.num_1);
        VerticalTextView name1 = (VerticalTextView) root.findViewById(R.id.name1);
        VerticalTextView enname1 = (VerticalTextView) root.findViewById(R.id.enname1);

        name1.setText(cipai1.getName() + "");
        enname1.setText(cipai1.getEnname() + "");
        addView(root);

    }

}
