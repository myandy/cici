package com.myth.cici.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.adapter.ImageAdapter;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.entity.Writing;
import com.myth.cici.util.ResizeUtil;
import com.myth.cici.wiget.HorizontalListView;

public class ChangeBackgroundFragment extends Fragment
{

    private Context mContext;

    private LinearLayout content;

    private TextView text;

    private Cipai cipai;

    private Writing writing;

    private int bg_index = 0;
    
    private TextView title;

    public ChangeBackgroundFragment()
    {
    }

    public static ChangeBackgroundFragment getInstance(Cipai cipai, Writing writing)
    {
        ChangeBackgroundFragment fileViewFragment = new ChangeBackgroundFragment();
        fileViewFragment.cipai = cipai;
        fileViewFragment.writing = writing;
        return fileViewFragment;
    }

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

    @Override
    public void onStop()
    {
        super.onStop();
        save();
    }

    public void save()
    {
        writing.setBgimg(bg_index + "");
    }

    private void refresh()
    {
        text.setText(writing.getText());
        content.setBackgroundResource(MyApplication.bgimgList[bg_index]);
    }

    private void initViews(View view)
    {
        HorizontalListView imgs = (HorizontalListView) view.findViewById(R.id.imgs);

        ImageAdapter adapter = new ImageAdapter(mContext);
        imgs.setAdapter(adapter);
        imgs.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                bg_index = position;
                content.setBackgroundResource(MyApplication.bgimgList[position]);
            }
        });

        content = (LinearLayout) view.findViewById(R.id.content);
        layoutItemContainer(content);
        title = (TextView) view.findViewById(R.id.title);
        title.setText(cipai.getName());
        text = (TextView) view.findViewById(R.id.text);
        title.setTypeface(MyApplication.getTypeface());
        text.setTypeface(MyApplication.getTypeface());
        
        setTextSize();
        setGravity();
        setPadding();
        setColor();
    }

    private void layoutItemContainer(View itemContainer)
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemContainer.getLayoutParams();
        params.width = ResizeUtil.resize(mContext, 720);
        params.height = ResizeUtil.resize(mContext, 720);
        itemContainer.setLayoutParams(params);
    }
    
    private void setPadding()
    {
        int margin = MyApplication.getDefaultSharePadding(mContext);
        LinearLayout.LayoutParams lps = (android.widget.LinearLayout.LayoutParams) text.getLayoutParams();
        lps.leftMargin = margin;
        text.setLayoutParams(lps);
    }

    private void setGravity()
    {
        boolean isCenter = MyApplication.getDefaultShareGravity(mContext);
        if (isCenter)
        {
            text.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        else
        {
            text.setGravity(Gravity.LEFT);
        }
    }

    private void setTextSize()
    {
        int size = MyApplication.getDefaultShareSize(mContext);
        text.setTextSize(size);
        title.setTextSize(size + 2);
    }

    private void setColor()
    {

        ColorEntity colorEntity = MyApplication.getColorByPos(MyApplication.getDefaultShareColor(mContext));
        int color = Color.rgb(0, 0, 0);
        if (colorEntity != null)
        {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        }
        text.setTextColor(color);
        title.setTextColor(color);
    }
}
