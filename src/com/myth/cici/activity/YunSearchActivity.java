package com.myth.cici.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.db.YunDatabaseHelper;
import com.myth.cici.entity.Yun;
import com.myth.cici.util.OthersUtils;

public class YunSearchActivity extends BaseActivity
{

    private Yun yun;

    private TextView yunTitle;

    private TextView yunzi;

    private View clear;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setBottomGone();
        YunDatabaseHelper.getYunList(mActivity);
        initView();
    }

    private void initView()
    {
        final EditText search = (EditText) findViewById(R.id.search);
        search.setHint(String.format(getResources().getString(R.string.search_hint),
                YunDatabaseHelper.YUNString[YunDatabaseHelper.getDefaultYunShu(mActivity)]));
        search.setHintTextColor(getResources().getColor(R.color.black_hint));
        search.setTextColor(getResources().getColor(R.color.black));
        findViewById(R.id.exit).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        yunTitle = (TextView) findViewById(R.id.yun_title);
        yunzi = (TextView) findViewById(R.id.yun_zi);
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                search.setText("");
                search.requestFocus();
            }
        });
        search.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (TextUtils.isEmpty(s.toString()))
                {
                    clear.setVisibility(View.GONE);
                }
                else
                {
                    clear.setVisibility(View.VISIBLE);
                }
                String str = OthersUtils.getFirstChinese(s.toString());
                if (!TextUtils.isEmpty(str))
                {
                    yun = YunDatabaseHelper.getSameYun(str);
                    if (yun != null)
                    {
                        yunTitle.setText(str + ": " + yun.getSection_desc() + "  " + yun.getTone_name());
                        yunzi.setText(yun.getGlys());
                    }
                }
            }
        });
    }
}
