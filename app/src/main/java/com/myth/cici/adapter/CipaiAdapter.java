package com.myth.cici.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.entity.Cipai;
import com.myth.cici.listener.MyListener;
import com.myth.cici.wiget.StoneView;

import java.util.List;

public class CipaiAdapter extends RecyclerView.Adapter<CipaiAdapter.ViewHolder> {

    private final MyApplication myApplication;
    private List<Cipai> list;

    private ViewHolder holder;

    private MyListener myListener;

    public void setList(List<Cipai> list) {
        this.list = list;
    }

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private MyListener myListener;

        public ViewHolder(View arg0) {
            super(arg0);
            name = (TextView) arg0.findViewById(R.id.name);
            tag = (TextView) arg0.findViewById(R.id.tag);
            arg0.setOnClickListener(this);
        }

        TextView name;

        TextView tag;

        /**
         * 点击监听
         */
        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(getPosition());
            }
        }

        public void setMyListener(MyListener myListener) {
            this.myListener = myListener;
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CipaiAdapter(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CipaiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cipai, parent, false);
        // set the view's size, margins, paddings and layout parameters

        holder = new ViewHolder(convertView);
        holder.myListener = myListener;

        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.name.setText(list.get(position).getName());
        holder.tag.setText(StoneView.getYunString(list.get(position).getTone_type()) + " ● "
                + list.get(position).getWordcount());

        holder.name.setTypeface(myApplication.getTypeface());
        holder.tag.setTypeface(myApplication.getTypeface());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

}
