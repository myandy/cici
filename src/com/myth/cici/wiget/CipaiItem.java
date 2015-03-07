package com.myth.cici.wiget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.activity.CipaiActivity;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;

public class CipaiItem extends RelativeLayout
{

    ViewHolder holder1;

    ViewHolder holder2;

    private Context mContext;

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
        holder1 = new ViewHolder();
        holder2 = new ViewHolder();
        holder1.cipai = cipai1;
        holder2.cipai = cipai2;
        mContext = context;
        initView();
    }

    private void initView()
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_cipai_item, null);

        holder1.item = root.findViewById(R.id.item1);
        holder1.head = (RelativeLayout) root.findViewById(R.id.head1);
        holder1.middle = (ViewGroup) root.findViewById(R.id.middle1);
        holder1.num = (VerticalTextView) root.findViewById(R.id.num_1);
        holder1.name = (TextView) root.findViewById(R.id.name1);
        holder1.enname = (VerticalTextView) root.findViewById(R.id.enname1);

        holder2.item = root.findViewById(R.id.item2);
        holder2.head = (RelativeLayout) root.findViewById(R.id.head2);
        holder2.middle = (ViewGroup) root.findViewById(R.id.middle2);
        holder2.num = (VerticalTextView) root.findViewById(R.id.num_2);
        holder2.name = (TextView) root.findViewById(R.id.name2);
        holder2.enname = (VerticalTextView) root.findViewById(R.id.enname2);

        holder1.name.setTypeface(MyApplication.typeface);
        holder2.name.setTypeface(MyApplication.typeface);
        holder1.enname.setTypeface(MyApplication.typeface);
        holder2.enname.setTypeface(MyApplication.typeface);

        initHolderView(holder1);
        initHolderView(holder2);

        addView(root);

    }

    private void initHolderView(final ViewHolder holder)
    {

        if (holder.cipai != null)
        {
            holder.item.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, CipaiActivity.class);
                    intent.putExtra("cipai", holder.cipai);
                    mContext.startActivity(intent);
                }
            });

            ColorEntity colorEntity = MyApplication.getColorById(holder.cipai.getColor_id());
            int color = 0xffffff;
            if (colorEntity != null)
            {
                color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
            }
            holder.head.setBackgroundColor(color);
            holder.num.setTextColor(color);
            holder.name.setTextColor(color);
            holder.enname.setTextColor(color);

            android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(100,
                    100);
            holder.middle.addView(new StoneView(mContext, holder.cipai.getTone_type(), color), 0, layoutParams);
            String count = holder.cipai.getWordcount() + "";
            if (holder.cipai.getWordcount() < 100)
            {
                count = "0" + holder.cipai.getWordcount();
            }
            holder.num.setText(count);
            holder.name.setText(holder.cipai.getName() + "");
            holder.enname.setText(holder.cipai.getEnname() + "");
        }
    }

    public class ViewHolder
    {
        Cipai cipai;

        View item;

        RelativeLayout head;

        ViewGroup middle;

        VerticalTextView num;

        TextView name;

        VerticalTextView enname;
    }

}
