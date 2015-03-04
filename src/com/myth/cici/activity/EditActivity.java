package com.myth.cici.activity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.entity.Cipai;
import com.myth.cici.util.CheckUtils;

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
            TextView textview = new TextView(this);
            EditText edittext = new EditText(this);
            textview.setText(sList[i] + "。");
            editContent.addView(textview);
            editContent.addView(edittext);
        }

        initView();
    }

    private void initView()
    {

    }


}
