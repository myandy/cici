package com.myth.cici.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.entity.Ci;
import com.myth.cici.entity.Cipai;

public class ShareActivity extends BaseActivity
{

    private ArrayList<Ci> ciList = new ArrayList<Ci>();

    private Cipai cipai;

    private int num;

    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        if (getIntent().hasExtra("cilist"))
        {
            ciList = (ArrayList<Ci>) getIntent().getSerializableExtra("cilist");
            cipai = (Cipai) getIntent().getSerializableExtra("cipai");
            num = getIntent().getIntExtra("num", 0);
        }

        initView();
    }

    private void initView()
    {
        LinearLayout imgsLayout = (LinearLayout) findViewById(R.id.imgs);

        for (int i = 0; i < MyApplication.bgimgList.length; i++)
        {
            ImageView imageView = new ImageView(mActivity);
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

        content = (TextView) findViewById(R.id.content);
        
        content.setText("仄平平中仄平，中平中仄仄平平。『中平中仄平平仄，中仄平平中仄平』。『平仄仄，仄平平』。中平中仄仄平平。中平中仄平平仄，中仄平平中仄平。");

        content.setBackgroundResource(MyApplication.bgimgList[0]);

    }

}
