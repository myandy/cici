package com.myth.cici.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.activity.CipaiActivity;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.util.DisplayUtil;
import com.myth.cici.wiget.StoneView;
import com.myth.cici.wiget.VerticalTextView;

public class CipaiListAdapter extends BaseAdapter
{

    ViewHolder holder1;

    ViewHolder holder2;

    private Context mContext;

    private List<Cipai> list;

    public CipaiListAdapter(Context context)
    {
        mContext = context;
    }

    public int getCount()
    {
        return list == null ? 0 : list.size() / 2;
    }

    public List<Cipai> getList()
    {
        return list;
    }

    public void setList(List<Cipai> list)
    {
        this.list = list;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_cipai_item, null);
            holder = new ViewHolder();
            View view = convertView.findViewById(R.id.content);
            view.setLayoutParams(new LinearLayout.LayoutParams(-2, parent.getMeasuredHeight()));
            holder.holder1 = new ViewHolderItem();
            holder.holder2 = new ViewHolderItem();
            holder.holder1.item = convertView.findViewById(R.id.item1);
            holder.holder1.head = (RelativeLayout) convertView.findViewById(R.id.head1);
            holder.holder1.middle = (ViewGroup) convertView.findViewById(R.id.middle1);
            holder.holder1.num = (TextView) convertView.findViewById(R.id.num_1);
            holder.holder1.name = (TextView) convertView.findViewById(R.id.name1);
            holder.holder1.enname = (VerticalTextView) convertView.findViewById(R.id.enname1);
            holder.holder1.stoneView = new StoneView(mContext);
            android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(
                    DisplayUtil.dip2px(mContext, 40), DisplayUtil.dip2px(mContext, 40));
            holder.holder1.middle.addView(holder.holder1.stoneView, layoutParams);

            holder.holder2.item = convertView.findViewById(R.id.item2);
            holder.holder2.head = (RelativeLayout) convertView.findViewById(R.id.head2);
            holder.holder2.middle = (ViewGroup) convertView.findViewById(R.id.middle2);
            holder.holder2.num = (TextView) convertView.findViewById(R.id.num_2);
            holder.holder2.name = (TextView) convertView.findViewById(R.id.name2);
            holder.holder2.enname = (VerticalTextView) convertView.findViewById(R.id.enname2);
            holder.holder2.stoneView = new StoneView(mContext);
            holder.holder2.middle.addView(holder.holder2.stoneView, layoutParams);

            holder.holder1.name.setTypeface(MyApplication.typeface);
            holder.holder2.name.setTypeface(MyApplication.typeface);
            holder.holder1.enname.setTypeface(MyApplication.typeface);
            holder.holder2.enname.setTypeface(MyApplication.typeface);
            holder.holder1.num.setTypeface(MyApplication.typeface);
            holder.holder2.num.setTypeface(MyApplication.typeface);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        initHolderView(holder.holder1, 2 * position);
        initHolderView(holder.holder2, 2 * position + 1);

        return convertView;
    }

    public class ViewHolder
    {
        ViewHolderItem holder1;

        ViewHolderItem holder2;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    private void initHolderView(final ViewHolderItem holder, int pos)
    {
        holder.cipai = list.get(pos);
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

            holder.stoneView.setType(holder.cipai.getTone_type(), color);
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

    public class ViewHolderItem
    {
        Cipai cipai;

        View item;

        RelativeLayout head;

        ViewGroup middle;

        TextView num;

        TextView name;

        VerticalTextView enname;

        StoneView stoneView;
    }

}
