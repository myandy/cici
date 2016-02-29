package com.myth.cici.fragment;

import android.app.Activity;
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

public class ChangeBackgroundFragment extends Fragment {

    private Context mContext;

    private LinearLayout content;

    private TextView text;

    private Cipai cipai;

    private Writing writing;

    private int bg_index = 0;

    private TextView title;
    private MyApplication myApplication;

    public ChangeBackgroundFragment() {
    }

    public static ChangeBackgroundFragment getInstance(Cipai cipai, Writing writing) {
        ChangeBackgroundFragment fileViewFragment = new ChangeBackgroundFragment();
        fileViewFragment.cipai = cipai;
        fileViewFragment.writing = writing;
        return fileViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        myApplication = (MyApplication) ((Activity) mContext).getApplication();
        View view = inflater.inflate(R.layout.fragment_background, null);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        save();
    }

    public void save() {
        writing.setBgimg(bg_index + "");
    }

    private void refresh() {
        text.setText(writing.getText());
        content.setBackgroundResource(myApplication.bgimgList[bg_index]);
        title.setText(cipai.getName());
    }

    private void initViews(View view) {
        HorizontalListView imgs = (HorizontalListView) view.findViewById(R.id.imgs);

        ImageAdapter adapter = new ImageAdapter(mContext);
        imgs.setAdapter(adapter);
        imgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bg_index = position;
                content.setBackgroundResource(myApplication.bgimgList[position]);
            }
        });

        content = (LinearLayout) view.findViewById(R.id.content);
        layoutItemContainer(content);
        title = (TextView) view.findViewById(R.id.title);
        text = (TextView) view.findViewById(R.id.text);
        title.setTypeface(myApplication.getTypeface());
        text.setTypeface(myApplication.getTypeface());

        setTextSize();
        setGravity();
        setPadding();
        setColor();
    }

    private void layoutItemContainer(View itemContainer) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemContainer.getLayoutParams();
        params.width = ResizeUtil.resize(mContext, 720);
        params.height = ResizeUtil.resize(mContext, 720);
        itemContainer.setLayoutParams(params);
    }

    private void setPadding() {
        int margin = myApplication.getDefaultSharePadding(mContext);
        LinearLayout.LayoutParams lps = (android.widget.LinearLayout.LayoutParams) text.getLayoutParams();
        lps.leftMargin = margin;
        text.setLayoutParams(lps);
    }

    private void setGravity() {
        boolean isCenter = myApplication.getDefaultShareGravity(mContext);
        if (isCenter) {
            text.setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            text.setGravity(Gravity.LEFT);
        }
    }

    private void setTextSize() {
        int size = myApplication.getDefaultShareSize(mContext);
        text.setTextSize(size);
        title.setTextSize(size + 2);
    }

    private void setColor() {

        ColorEntity colorEntity = myApplication.getColorByPos(myApplication.getDefaultShareColor(mContext));
        int color = Color.rgb(0, 0, 0);
        if (colorEntity != null) {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        }
        text.setTextColor(color);
        title.setTextColor(color);
    }
}
