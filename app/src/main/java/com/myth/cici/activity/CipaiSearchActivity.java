package com.myth.cici.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.adapter.CipaiAdapter;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Cipai;
import com.myth.cici.listener.MyListener;

import java.util.ArrayList;

public class CipaiSearchActivity extends BaseActivity {

    private View clear;

    private RecyclerView listview;

    private CipaiAdapter adapter;

    private ArrayList<Cipai> ciList;

    private ArrayList<Cipai> sortList;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cipai);
        setBottomGone();
        ciList = CipaiDatabaseHelper.getAllCipai();
        initView();
        refreshData();
    }

    private void refreshData() {
        String word = search.getText().toString().trim();
        if (TextUtils.isEmpty(word)) {
            sortList = ciList;
        } else {
            sortList = searchCipai(word);
        }
        adapter.setList(sortList);
        adapter.notifyDataSetChanged();

    }

    private ArrayList<Cipai> searchCipai(String word) {
        ArrayList<Cipai> list = new ArrayList<Cipai>();
        for (Cipai cipai : ciList) {
            if (cipai.getName().contains(word)) {
                list.add(cipai);
            }
        }
        return list;
    }

    private void initView() {
        listview = (RecyclerView) findViewById(R.id.listview);

        listview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        listview.setLayoutManager(linearLayoutManager);

        adapter = new CipaiAdapter(myApplication);
        adapter.setMyListener(new MyListener() {

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity, EditActivity.class);
                intent.putExtra("cipai", sortList.get(position));
                startActivity(intent);
                finish();
            }
        });
        listview.setAdapter(adapter);

        // listview.setOnItemClickListener(new OnItemClickListener()
        // {
        //
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view, int
        // position, long id)
        // {
        // Intent intent = new Intent(mActivity, EditActivity.class);
        // intent.putExtra("cipai", sortList.get(position));
        // startActivity(intent);
        // finish();
        // }
        // });

        search = (EditText) findViewById(R.id.search);

        search.setHint(R.string.search_cipai_hint);
        search.setHintTextColor(getResources().getColor(R.color.black_hint));
        search.setTextColor(getResources().getColor(R.color.black));
        findViewById(R.id.exit).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                search.setText("");
                search.requestFocus();
            }
        });
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    clear.setVisibility(View.GONE);
                } else {
                    clear.setVisibility(View.VISIBLE);
                }
                refreshData();
            }
        });
    }
}
