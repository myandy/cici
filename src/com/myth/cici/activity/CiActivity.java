package com.myth.cici.activity;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.db.CiDatabaseHelper;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Ci;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.wiget.CircleEditView;
import com.myth.cici.wiget.TouchEffectImageView;

public class CiActivity extends BaseActivity
{

    private ArrayList<Ci> ciList = new ArrayList<Ci>();

    private Cipai cipai;

    private int num;

    private TextView content;

    private boolean isIntroduce = false;

    private boolean isRandom = false;

    private Ci ci;

    private TextView title;

    private CircleEditView editView;

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
            else
            {
                ci = ciList.get(num);
            }
        }
        else
        {
            isRandom = true;
            ciList = CiDatabaseHelper.getAllCi();
            ci = ciList.get(new Random().nextInt(ciList.size()));
            cipai = CipaiDatabaseHelper.getCipaiById(ci.getCi_id());
        }

        initView();
    }

    private void setColor()
    {
        ColorEntity colorEntity = MyApplication.getColorById(cipai.getColor_id());
        int color = 0xffffff;
        if (colorEntity != null)
        {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        }
        editView.setmColor(color);
    }

    private void initView()
    {
        LinearLayout topView = (LinearLayout) findViewById(R.id.right);
        LayoutParams param = new LayoutParams(200, 300);
        editView = new CircleEditView(mActivity);
        topView.addView(editView, 1, param);
        setColor();

        title = (TextView) findViewById(R.id.title);
        title.setTypeface(MyApplication.typeface);
        title.setText(cipai.getName());

        content = (TextView) findViewById(R.id.content);
        ((TextView) findViewById(R.id.author)).setTypeface(MyApplication.typeface);
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
                    Intent intent = new Intent(mActivity, ShareEditActivity.class);
                    ci.setCipai(cipai);
                    intent.putExtra("ci", ci);
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

        initBottomRightView();

    }

    private void initBottomRightView()
    {
        if (isIntroduce)
        {
            return;
        }
        else if (isRandom)
        {
            ImageView view = new TouchEffectImageView(mActivity, null);
            view.setImageResource(R.drawable.random);
            view.setScaleType(ScaleType.FIT_XY);
            view.setPadding(5, 5, 5, 5);
            addBottomRightView(view, new LayoutParams(76, 60));
            view.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    ci = ciList.get(new Random().nextInt(ciList.size()));
                    cipai = CipaiDatabaseHelper.getCipaiById(ci.getCi_id());
                    if (cipai.getParent_id() > 0)
                    {
                        Cipai cipai1 = CipaiDatabaseHelper.getCipaiById(cipai.getParent_id());
                        cipai.setColor_id(cipai1.getColor_id());
                    }
                    refreshRandomView();
                }
            });
        }
        else
        {
            ImageView prev = new TouchEffectImageView(mActivity, null);
            prev.setImageResource(R.drawable.prev);
            prev.setScaleType(ScaleType.FIT_XY);
            addBottomRightView(prev, new LayoutParams(106, 106));
            prev.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    if (num > 1)
                    {
                        num--;
                        ci = ciList.get(num);
                        initContentView();
                    }
                }
            });

            ImageView next = new TouchEffectImageView(mActivity, null);
            next.setImageResource(R.drawable.next);
            next.setScaleType(ScaleType.FIT_XY);
            addBottomRightView(next, new LayoutParams(106, 106));
            next.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    if (num < ciList.size() - 1)
                    {
                        num++;
                        ci = ciList.get(num);
                        initContentView();
                    }

                }
            });
        }

    }

    private void refreshRandomView()
    {
        title.setText(cipai.getName());
        setColor();
        initContentView();
    }

    private void initContentView()
    {
        if (isIntroduce)
        {
            WebView intro = (WebView) findViewById(R.id.intro);
            intro.setBackgroundColor(0);
            intro.loadUrl("file:///android_asset/intro.html");
            content.setText(cipai.getSource());
        }
        else
        {

            String note = ci.getNote();
            if (note == null)
            {
                note = "";
            }
            content.setText(ci.getText());
            ((TextView) findViewById(R.id.note)).setText(note);
            ((TextView) findViewById(R.id.author)).setText(ci.getAuthor() + "\n");
        }
    }

}
