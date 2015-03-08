package com.myth.cici.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.entity.Ci;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.util.OthersUtils;
import com.myth.cici.wiget.CircleEditView;

public class CiActivity extends BaseActivity
{

    private ArrayList<Ci> ciList = new ArrayList<Ci>();

    private Cipai cipai;

    private int num;

    private TextView content;

    private boolean isIntroduce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ci);

        if (getIntent().hasExtra("cipai"))
        {
            ciList = (ArrayList<Ci>) getIntent().getSerializableExtra("cilist");
            cipai = (Cipai) getIntent().getSerializableExtra("cipai");
            num = getIntent().getIntExtra("num", 0);
            if (num == 0)
            {
                isIntroduce = true;
            }
        }

        initView();
    }

    private void initView()
    {
        ColorEntity colorEntity = MyApplication.getColorById(cipai.getColor_id());
        int color = 0xffffff;
        if (colorEntity != null)
        {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        }

        LinearLayout topView = (LinearLayout) findViewById(R.id.right);

        LayoutParams param = new LayoutParams(200, 300);
        CircleEditView editView = new CircleEditView(mActivity, color);
        topView.addView(editView, 1, param);

        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(MyApplication.typeface);
        title.setText(cipai.getName());

        content = (TextView) findViewById(R.id.content);
        content.setTypeface(MyApplication.typeface);
        if (isIntroduce)
        {
            findViewById(R.id.share).setVisibility(View.GONE);
        }
        else
        {
            findViewById(R.id.share).setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mActivity, ShareActivity.class);

                    startActivity(intent);
                }
            });
        }
        editView.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mActivity, EditActivity.class);
                intent.putExtra("cipai", cipai);
                startActivity(intent);
            }
        });

        initContentView();

    }

    private void initContentView()
    {
        if (isIntroduce)
        {
            String source = OthersUtils.readAssertResource(mActivity, "intro.html");
            TextView intro = (TextView) findViewById(R.id.intro);
            intro.setText(Html.fromHtml(source));
            content.setText(cipai.getSource());
        }
        else
        {
            String note = ciList.get(num).getNote();
            if (note == null)
            {
                note = "";
            }
            content.setText(ciList.get(num).getAuthor() + "\n" + ciList.get(num).getText() + "\n" + note);
        }
    }

}
