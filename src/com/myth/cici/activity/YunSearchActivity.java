package com.myth.cici.activity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.db.YunDatabaseHelper;
import com.myth.cici.entity.Yun;
import com.myth.cici.util.OthersUtils;

public class YunSearchActivity extends BaseActivity
{

    private List<Yun> yuns;

    private TextView yunTitle;

    private TextView yunzi;

    private TextView yunTitle2;

    private TextView yunzi2;

    private View clear;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setBottomGone();
        initView();
    }

    private void initView()
    {
        final EditText search = (EditText) findViewById(R.id.search);
        search.setFocusableInTouchMode(true);
        search.setFocusable(true);
        search.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {

            public void run()
            {
                InputMethodManager inputManager = (InputMethodManager) search.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(search, 0);
            }

        }, 50);

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

        yunTitle2 = (TextView) findViewById(R.id.yun_title2);
        yunzi2 = (TextView) findViewById(R.id.yun_zi2);

        yunzi.setVisibility(View.GONE);
        yunzi2.setVisibility(View.GONE);
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
                    yuns = YunDatabaseHelper.getSameYun(str);
                    if (yuns != null && yuns.size() > 0)
                    {
                        yunTitle.setVisibility(View.VISIBLE);
                        yunzi.setVisibility(View.VISIBLE);
                        yunTitle.setText(str + ": " + yuns.get(0).getSection_desc() + "  " + yuns.get(0).getTone_name());
                        yunzi.setText(yuns.get(0).getGlys());
                    }
                    else
                    {
                        yunTitle.setVisibility(View.GONE);
                        yunzi.setVisibility(View.GONE);
                    }

                    if (yuns != null && yuns.size() > 1)
                    {
                        yunTitle2.setVisibility(View.VISIBLE);
                        yunzi2.setVisibility(View.VISIBLE);
                        yunTitle2.setText(str + ": " + yuns.get(1).getSection_desc() + "  "
                                + yuns.get(1).getTone_name());
                        yunzi2.setText(yuns.get(1).getGlys());
                    }
                    else
                    {
                        yunTitle2.setVisibility(View.GONE);
                        yunzi2.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
