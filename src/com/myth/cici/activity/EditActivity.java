package com.myth.cici.activity;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.Writing;
import com.myth.cici.fragment.ChangeBackgroundFragment;
import com.myth.cici.fragment.ChangePictureFragment;
import com.myth.cici.fragment.EditFragment;
import com.myth.cici.wiget.SelectImageView;

public class EditActivity extends BaseActivity
{

    public static Cipai cipai;

    public static Writing writing = new Writing();

    ChangeBackgroundFragment changeBackgroundFrament;

    EditFragment editFragment;

    ChangePictureFragment changePictureFragment;

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        cipai = (Cipai) getIntent().getSerializableExtra("cipai");

        initView();
    }


    private void initView()
    {

        SelectImageView edit = new SelectImageView(mActivity, null);
        edit.setImageResource(R.drawable.edit);
        edit.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                changeFragment(0);
            }
        });

        SelectImageView background = new SelectImageView(mActivity, null);
        background.setImageResource(R.drawable.layout_bg_paper);

        background.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                changeFragment(1);
            }
        });

        SelectImageView picture = new SelectImageView(mActivity, null);
        picture.setImageResource(R.drawable.layout_bg_album);

        picture.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                changeFragment(2);
            }
        });

        addBottomCenterView(edit);
        addBottomCenterView(background);
        addBottomCenterView(picture);

        // 创建修改实例
        changeBackgroundFrament = new ChangeBackgroundFragment();
        editFragment = new EditFragment();
        changePictureFragment = new ChangePictureFragment();

        fragments.add(editFragment);
        fragments.add(changeBackgroundFrament);
        fragments.add(changePictureFragment);
        changeFragment(0);
    }

    public void changeFragment(int pos)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragments.get(pos));
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
