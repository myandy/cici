package com.myth.cici.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.activity.CiActivity;
import com.myth.cici.activity.YunSearchActivity;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.Writing;
import com.myth.cici.util.CheckUtils;
import com.myth.cici.util.StringUtils;
import com.myth.cici.wiget.PingzeLinearlayout;

public class EditFragment extends Fragment
{

    private LinearLayout editContent;

    private String[] sList;

    private Context mContext;

    private ArrayList<EditText> editTexts = new ArrayList<EditText>();

    private View root;

    private Cipai cipai;

    private Writing writing;

    public EditFragment(Cipai cipai2, Writing writing2)
    {
        setData(cipai2, writing2);
    }

    public void setData(Cipai cipai, Writing writing)
    {
        this.cipai = cipai;
        this.writing = writing;
    }

    @Override
    public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
            Bundle savedInstanceState)
    {

        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        root = inflater.inflate(R.layout.fragment_edit, null);
        initViews(root);
        return root;

    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (StringUtils.isNumeric(writing.getBgimg()))
        {
            root.setBackgroundResource(MyApplication.bgimgList[Integer.parseInt(writing.getBgimg())]);
        }
        else if (writing.getBitmap() != null)
        {
            root.setBackgroundDrawable(new BitmapDrawable(getResources(), writing.getBitmap()));
        }
        else
        {
            root.setBackgroundDrawable(new BitmapDrawable(getResources(), writing.getBgimg()));
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        save();
    }

    public void save()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < editTexts.size(); i++)
        {
            sb.append(editTexts.get(i).getEditableText().toString() + "\n");
        }
        writing.setText(sb.toString());
    }

    private void initViews(View view)
    {
        editTexts.clear();
        final View keyboard = view.findViewById(R.id.edit_keyboard);
        editContent = (LinearLayout) view.findViewById(R.id.edit_content);
        String s = Html.fromHtml(cipai.getPingze()).toString();
        sList = CheckUtils.getCodeFormPingze(s.split("。"));

        if (sList != null)
        {
            String[] tList = null;
            if (writing.getText() != null)
            {
                tList = writing.getText().split("\n");
            }
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < sList.length; i++)
            {
                View view1 = new PingzeLinearlayout(mContext, sList[i]);
                view1.setPadding(0, 30, 0, 30);
                final EditText edittext = (EditText) inflater.inflate(R.layout.edittext, null);
                edittext.setTypeface(MyApplication.typeface);
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
                        else
                        {
                            keyboard.setVisibility(View.VISIBLE);
                            ((BaseActivity) mContext).setBottomGone();
                        }
                    }
                });
                editContent.addView(view1);
                editContent.addView(edittext);
                editTexts.add(edittext);

                if (i == 0)
                {
                    edittext.requestFocus();
                }

                if (tList != null && tList.length > i)
                {
                    edittext.setText(tList[i]);
                }
            }
        }
        else
        {
            Log.e("EditFragment", "sList is null");
        }

        TextView title = (TextView) view.findViewById(R.id.edit_title);
        title.setText(cipai.getName());
        title.setTypeface(MyApplication.typeface);

        view.findViewById(R.id.edit_dict).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, YunSearchActivity.class);
                mContext.startActivity(intent);
            }
        });
        view.findViewById(R.id.edit_info).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, CiActivity.class);
                intent.putExtra("cipai", cipai);
                intent.putExtra("num", 0);
                startActivity(intent);
            }
        });
        final View getfocus = view.findViewById(R.id.getfocus);
        getfocus.setFocusable(true);
        getfocus.setFocusableInTouchMode(true);

        keyboard.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                keyboard.setVisibility(View.GONE);
                ((BaseActivity) mContext).setBottomVisible();
                hideSoftInputFromWindow();
                getfocus.requestFocus();
                getfocus.requestFocusFromTouch();
            }
        });
    }

    private void hideSoftInputFromWindow()
    {
        View view = ((Activity) mContext).getWindow().peekDecorView();
        if (view != null)
        {
            InputMethodManager inputmanger = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
