package com.myth.cici.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.activity.CiActivity;
import com.myth.cici.activity.EditActivity;
import com.myth.cici.activity.YunSearchActivity;
import com.myth.cici.entity.Cipai;
import com.myth.cici.util.CheckUtils;
import com.myth.cici.wiget.PingzeLinearlayout;

public class EditFragment extends Fragment
{

    private Cipai cipai;

    private LinearLayout editContent;

    private String[] sList;
    
    private Context mContext;

    @Override
    public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
        
        
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_edit, null);
        cipai = EditActivity.cipai;
        initViews(view);
        return view;
        

    }
    private void initViews(View view)
    {
        editContent = (LinearLayout) view.findViewById(R.id.edit_content);
        String s = Html.fromHtml(cipai.getPingze()).toString();
        sList = CheckUtils.getCodeFormPingze(s.split("。"));

        for (int i = 0; i < sList.length; i++)
        {
            View view1 = new PingzeLinearlayout(mContext, sList[i]);
            view1.setPadding(0, 20, 0, 0);
            final EditText edittext = new EditText(mContext);
            edittext.setTextColor(mContext.getResources().getColor(R.color.black));

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
            editContent.addView(view1);
            editContent.addView(edittext);
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
    }


}
