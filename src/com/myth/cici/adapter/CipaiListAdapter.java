package com.myth.cici.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.activity.CipaiActivity;
import com.myth.cici.adapter.CipaiListAdapter.ViewHolder.ViewHolderItem;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.util.DisplayUtil;
import com.myth.cici.wiget.StoneView;
import com.myth.cici.wiget.VerticalTextView;

public class CipaiListAdapter extends RecyclerView.Adapter<CipaiListAdapter.ViewHolder>
{
    private Context mContext;

    private List<Cipai> list;

    public void setList(List<Cipai> list)
    {
        this.list = list;
    }

    public CipaiListAdapter(Context context)
    {
        mContext = context;
    }

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ViewHolderItem holder1;

        ViewHolderItem holder2;

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

        public ViewHolder(View convertView, ViewGroup parent)
        {
            super(convertView);
            View view = convertView.findViewById(R.id.content);
            view.setLayoutParams(new LinearLayout.LayoutParams(-2, parent.getMeasuredHeight()));
            holder1 = new ViewHolderItem();
            holder2 = new ViewHolderItem();
            holder1.item = convertView.findViewById(R.id.item1);
            holder1.head = (RelativeLayout) convertView.findViewById(R.id.head1);
            holder1.middle = (ViewGroup) convertView.findViewById(R.id.middle1);
            holder1.num = (TextView) convertView.findViewById(R.id.num_1);
            holder1.name = (TextView) convertView.findViewById(R.id.name1);
            holder1.enname = (VerticalTextView) convertView.findViewById(R.id.enname1);
            holder1.stoneView = new StoneView(parent.getContext());
            android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(
                    DisplayUtil.dip2px(parent.getContext(), 40), DisplayUtil.dip2px(parent.getContext(), 40));
            holder1.middle.addView(holder1.stoneView, layoutParams);

            holder2.item = convertView.findViewById(R.id.item2);
            holder2.head = (RelativeLayout) convertView.findViewById(R.id.head2);
            holder2.middle = (ViewGroup) convertView.findViewById(R.id.middle2);
            holder2.num = (TextView) convertView.findViewById(R.id.num_2);
            holder2.name = (TextView) convertView.findViewById(R.id.name2);
            holder2.enname = (VerticalTextView) convertView.findViewById(R.id.enname2);
            holder2.stoneView = new StoneView(parent.getContext());
            holder2.middle.addView(holder2.stoneView, layoutParams);

            holder1.name.setTypeface(MyApplication.getTypeface());
            holder2.name.setTypeface(MyApplication.getTypeface());
            holder1.enname.setTypeface(MyApplication.getTypeface());
            holder2.enname.setTypeface(MyApplication.getTypeface());
            holder1.num.setTypeface(MyApplication.getTypeface());
            holder2.num.setTypeface(MyApplication.getTypeface());
        }

        TextView name;

        TextView tag;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CipaiListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        mContext = parent.getContext();
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cipai_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder holder = new ViewHolder(convertView, parent);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        initHolderView(holder.holder1, 2 * position);
        initHolderView(holder.holder2, 2 * position + 1);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return list == null ? 0 : list.size() / 2;
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
                    final ArrayList<Cipai> cipais=CipaiDatabaseHelper.getParentCipaiById(holder.cipai.getId());
                    
                   String [] titles=new String[cipais.size()];
                    
                    for(int i=0;i<cipais.size();i++){
                        titles[i]=cipais.get(i).getName();
                        if(TextUtils.isEmpty(cipais.get(i).getSource())){
                            cipais.get(i).setSource(holder.cipai.getSource());
                        }
                        cipais.get(i).setColor_id(holder.cipai.getColor_id());
                    }
                    if(cipais.size()>1){
                        new AlertDialog.Builder(mContext).setItems(titles, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(mContext, CipaiActivity.class);
                                intent.putExtra("cipai",cipais.get(which));
                                mContext.startActivity(intent);
                                dialog.dismiss();
                            }
                        }).show();
                    }
                    else{
                    Intent intent = new Intent(mContext, CipaiActivity.class);
                    intent.putExtra("cipai", holder.cipai);
                    mContext.startActivity(intent);}
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

}
