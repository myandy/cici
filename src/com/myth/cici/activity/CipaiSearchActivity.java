package com.myth.cici.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.adapter.CipaiAdapter;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Cipai;

public class CipaiSearchActivity extends BaseActivity
{

    private View clear;

    private ListView listview;

    private CipaiAdapter adapter;

    private ArrayList<Cipai> ciList;

    private ArrayList<Cipai> sortList;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cipai);
        setBottomGone();
        ciList = CipaiDatabaseHelper.getAllCipai();
        initView();
        refreshData();
    }

    private void refreshData()
    {
        String word = search.getText().toString().trim();
        if (TextUtils.isEmpty(word))
        {
            sortList = ciList;
        }
        else
        {
            sortList = searchCipai(word);
        }
        adapter.setList(sortList);
        adapter.notifyDataSetChanged();

    }

    private ArrayList<Cipai> searchCipai(String word)
    {
        ArrayList<Cipai> list = new ArrayList<Cipai>();
        for (Cipai cipai : ciList)
        {
            if (cipai.getName().contains(word))
            {
                list.add(cipai);
            }
        }
        return list;
    }

    private void initView()
    {
        listview = (ListView) findViewById(R.id.listview);
        adapter = new CipaiAdapter(mActivity);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(mActivity, EditActivity.class);
                intent.putExtra("cipai", sortList.get(position));
                startActivity(intent);
                finish();
            }
        });

        search = (EditText) findViewById(R.id.search);

        search.setHint(R.string.search_cipai_hint);
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
                refreshData();
            }
        });
    }
}
