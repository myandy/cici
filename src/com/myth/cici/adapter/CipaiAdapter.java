package com.myth.cici.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.entity.Cipai;
import com.myth.cici.wiget.StoneView;

public class CipaiAdapter extends BaseAdapter
{

    private Context mContext;

    private List<Cipai> list;

    public void setList(List<Cipai> list)
    {
        this.list = list;
    }

    public CipaiAdapter(Context context)
    {
        mContext = context;
    }

    public int getCount()
    {
        return list == null ? 0 : list.size();
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
            convertView = inflater.inflate(R.layout.adapter_cipai, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.tag = (TextView) convertView.findViewById(R.id.tag);
            holder.name.setTypeface(MyApplication.typeface);
            holder.tag.setTypeface(MyApplication.typeface);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(list.get(position).getName());
        holder.tag.setText(StoneView.getYunString(list.get(position).getTone_type()) + " ● "
                + list.get(position).getWordcount());

        return convertView;
    }

    public class ViewHolder
    {
        TextView name;

        TextView tag;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

}
