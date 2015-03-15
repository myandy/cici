package com.myth.cici.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.db.WritingDatabaseHelper;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.Writing;
import com.myth.cici.fragment.ChangeBackgroundFragment;
import com.myth.cici.fragment.ChangePictureFragment;
import com.myth.cici.fragment.EditFragment;
import com.myth.cici.util.FileUtils;
import com.myth.cici.util.StringUtils;
import com.myth.cici.wiget.TouchEffectImageView;

public class EditActivity extends BaseActivity
{

    private Cipai cipai;

    private Writing writing;

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
        writing = (Writing) getIntent().getSerializableExtra("writing");

        if (writing == null)
        {
            writing = new Writing();
            writing.setId(writing.hashCode());
            writing.setCi_id(cipai.getId());
            writing.setBgimg("0");
        }
        else
        {
            cipai = writing.getCipai();
        }

        initView();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (!StringUtils.isEmpty(writing.getText()))
        {
            if (!StringUtils.isNumeric(writing.getBgimg()) && writing.getBitmap() != null)
            {
                try
                {
                    String fileName = FileUtils.saveFile(writing.getBitmap());
                    writing.setBgimg(fileName);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            WritingDatabaseHelper.saveWriting(mActivity, writing);

        }
    }

    private void initView()
    {

        final ImageView edit = new TouchEffectImageView(mActivity, null);
        edit.setScaleType(ScaleType.FIT_XY);
        edit.setImageResource(R.drawable.layout_bg_edit);

        final ImageView background = new TouchEffectImageView(mActivity, null);
        background.setScaleType(ScaleType.FIT_XY);
        background.setImageResource(R.drawable.layout_bg_paper);

        final ImageView picture = new TouchEffectImageView(mActivity, null);
        picture.setScaleType(ScaleType.FIT_XY);
        picture.setImageResource(R.drawable.layout_bg_album);

        edit.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                changeFragment(0);
                edit.setImageResource(R.drawable.layout_bg_edit_selected);
                background.setImageResource(R.drawable.layout_bg_paper);
                picture.setImageResource(R.drawable.layout_bg_album);

            }
        });

        background.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                changeFragment(1);
                edit.setImageResource(R.drawable.layout_bg_edit);
                background.setImageResource(R.drawable.layout_bg_paper_selected);
                picture.setImageResource(R.drawable.layout_bg_album);
            }
        });

        picture.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                changeFragment(2);
                edit.setImageResource(R.drawable.layout_bg_edit);
                background.setImageResource(R.drawable.layout_bg_paper);
                picture.setImageResource(R.drawable.layout_bg_album_sel);
            }
        });

        LayoutParams lps = new LayoutParams(99, 114);
        addBottomCenterView(edit, lps);
        addBottomCenterView(background, lps);
        addBottomCenterView(picture, lps);

        // 创建修改实例
        editFragment = new EditFragment();
        changeBackgroundFrament = new ChangeBackgroundFragment();
        changePictureFragment = new ChangePictureFragment();

        editFragment.setData(cipai, writing);
        changeBackgroundFrament.setData(cipai, writing);
        changePictureFragment.setData(cipai, writing);

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

    @Override
    public void onBackPressed()
    {
        finish();
    }

}
