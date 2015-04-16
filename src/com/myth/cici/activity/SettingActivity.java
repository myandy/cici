package com.myth.cici.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.db.YunDatabaseHelper;

public class SettingActivity extends BaseActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();


    }

    private void initView()
    {

        refreshYun();
        refreshTypeface();
        refreshCheck();

        ((TextView) findViewById(R.id.yun_title)).setTypeface(MyApplication.typeface);
        ((TextView) findViewById(R.id.yun_value)).setTypeface(MyApplication.typeface);
        ((TextView) findViewById(R.id.typeface_value)).setTypeface(MyApplication.typeface);
        ((TextView) findViewById(R.id.typeface_title)).setTypeface(MyApplication.typeface);
        ((TextView) findViewById(R.id.check_value)).setTypeface(MyApplication.typeface);
        ((TextView) findViewById(R.id.check_title)).setTypeface(MyApplication.typeface);
        ((TextView) findViewById(R.id.about_title)).setTypeface(MyApplication.typeface);
        ((TextView) findViewById(R.id.notice_title)).setTypeface(MyApplication.typeface);

        findViewById(R.id.item_yun).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(mActivity).setSingleChoiceItems(YunDatabaseHelper.YUNString,
                        YunDatabaseHelper.getDefaultYunShu(mActivity), new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                YunDatabaseHelper.setDefaultYunShu(mActivity, which);
                                refreshYun();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        findViewById(R.id.item_typeface).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(mActivity).setSingleChoiceItems(MyApplication.TypefaceString,
                        MyApplication.getDefaulTypeface(mActivity), new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                MyApplication.setDefaultTypeface(mActivity, which);
                                MyApplication.setTypeface(mActivity, MyApplication.getDefaulTypeface(mActivity));
                                refreshTypeface();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        findViewById(R.id.item_check).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String[] s = {mActivity.getString(R.string.check_true), mActivity.getString(R.string.check_false)};
                new AlertDialog.Builder(mActivity).setSingleChoiceItems(s,
                        MyApplication.getCheckAble(mActivity) ? 0 : 1, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                MyApplication.setCheckAble(mActivity, which == 0);
                                refreshCheck();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        findViewById(R.id.item_about).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(mActivity, AboutActivity.class));
            }
        });
    }

    private void refreshYun()
    {
        ((TextView) findViewById(R.id.yun_value)).setText(YunDatabaseHelper.YUNString[YunDatabaseHelper.getDefaultYunShu(mActivity)]);
    }

    private void refreshTypeface()
    {
        ((TextView) findViewById(R.id.typeface_value)).setText(MyApplication.TypefaceString[MyApplication.getDefaulTypeface(mActivity)]);
    }

    private void refreshCheck()
    {
        if (MyApplication.getCheckAble(mActivity))
        {
            ((TextView) findViewById(R.id.check_value)).setText(R.string.check_true);
        }
        else
        {
            ((TextView) findViewById(R.id.check_value)).setText(R.string.check_false);
        }
    }

}
