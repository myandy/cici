package com.myth.cici.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.activity.EditActivity;
import com.myth.cici.util.ResizeUtil;

public class ChangeBackgroundFragment extends Fragment
{

    private Context mContext;

    private LinearLayout content;

    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_background, null);
        initViews(view);
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        refresh();
    }

    private void refresh()
    {
        text.setText(EditActivity.writing.getText());
        int i = 0;
        try
        {
            i = Integer.parseInt(EditActivity.writing.getBgimg());
        }
        catch (Exception e)
        {
        }
        content.setBackgroundResource(MyApplication.bgimgList[i]);
    }

    private void initViews(View view)
    {
        LinearLayout imgsLayout = (LinearLayout) view.findViewById(R.id.imgs);

        for (int i = 0; i < MyApplication.bgimgList.length; i++)
        {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ScaleType.FIT_XY);
            LinearLayout.LayoutParams layoutParams = new LayoutParams(300, 300);
            imageView.setImageResource(MyApplication.bgimgList[i]);
            imgsLayout.addView(imageView, layoutParams);
            final int index = i;
            imageView.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    content.setBackgroundResource(MyApplication.bgimgList[index]);
                }
            });

        }

        content = (LinearLayout) view.findViewById(R.id.content);
        layoutItemContainer(content);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(EditActivity.cipai.getName());
        text = (TextView) view.findViewById(R.id.text);
    }

    private void layoutItemContainer(View itemContainer)
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemContainer.getLayoutParams();
        params.width = ResizeUtil.resize(mContext, 720);
        params.height = ResizeUtil.resize(mContext, 720);
        itemContainer.setLayoutParams(params);
    }
}
