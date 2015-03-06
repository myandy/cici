package com.myth.cici.activity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.db.YunDatabaseHelper;
import com.myth.cici.entity.Cipai;
import com.myth.cici.util.CheckUtils;
import com.myth.cici.wiget.PingzeLinearlayout;

public class EditActivity extends BaseActivity
{

    private Cipai cipai;

    private LinearLayout editContent;

    private String[] sList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        cipai = (Cipai) getIntent().getSerializableExtra("cipai");

        editContent = (LinearLayout) findViewById(R.id.edit_content);
        String s = Html.fromHtml(cipai.getPingze()).toString();
        Log.d("myth", s);
        sList = CheckUtils.getCodeFormPingze(s.split("。"));

        for (int i = 0; i < sList.length; i++)
        {
            View view = new PingzeLinearlayout(this, sList[i]);
            view.setPadding(0, 20, 0, 0);
            final EditText edittext = new EditText(this);
            edittext.setTextColor(mActivity.getResources().getColor(R.color.black));

            final int index = i;
            edittext.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener()
            {
                @Override
                public void onFocusChange(View v, boolean hasFocus)
                {
                    if (!hasFocus)
                    {
                        CheckUtils.checkEditText(edittext, sList[index]);
                    }
                }
            });
            editContent.addView(view);
            editContent.addView(edittext);
        }

        YunDatabaseHelper.getYunList(mActivity);

        initView();
    }

    private void initView()
    {
        TextView title = (TextView) findViewById(R.id.edit_title);
        title.setText(cipai.getName());
        title.setTypeface(MyApplication.typeface);

        findViewById(R.id.edit_dict).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

            }
        });
        findViewById(R.id.edit_info).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

            }
        });
    }


}
