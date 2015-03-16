package com.myth.cici.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.entity.Ci;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.Writing;
import com.myth.cici.fragment.ChangeBackgroundFragment;
import com.myth.cici.fragment.ChangePictureFragment;
import com.myth.cici.util.FileUtils;
import com.myth.cici.util.StringUtils;
import com.myth.cici.wiget.TouchEffectImageView;

public class ShareEditActivity extends BaseActivity
{

    private Cipai cipai;

    private Writing writing;

    private Ci ci;

    ChangeBackgroundFragment changeBackgroundFrament;

    ChangePictureFragment changePictureFragment;

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ci = (Ci) getIntent().getSerializableExtra("ci");

        writing = new Writing();
        writing.setText(ci.getText());
        cipai = ci.getCipai();
        writing.setCipai(cipai);

        ImageView down = new TouchEffectImageView(mActivity, null);
        down.setImageResource(R.drawable.done);
        down.setScaleType(ScaleType.FIT_XY);
        addBottomRightView(down, new LayoutParams(60, 60));
        down.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (currentIndex == 0)
                {
                    changeBackgroundFrament.save();
                }
                else
                {
                    changePictureFragment.save();
                }
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
                writing.setBitmap(null);
                Intent intent = new Intent(mActivity, ShareActivity.class);
                intent.putExtra("writing", writing);
                startActivity(intent);
                finish();
            }
        });

        initView();
    }

    private void initView()
    {

        final ImageView background = new TouchEffectImageView(mActivity, null);
        background.setScaleType(ScaleType.FIT_XY);
        background.setImageResource(R.drawable.layout_bg_paper);

        final ImageView picture = new TouchEffectImageView(mActivity, null);
        picture.setScaleType(ScaleType.FIT_XY);
        picture.setImageResource(R.drawable.layout_bg_album);

        background.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                changeFragment(0);
                background.setImageResource(R.drawable.layout_bg_paper_selected);
                picture.setImageResource(R.drawable.layout_bg_album);
            }
        });

        picture.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                changeFragment(1);
                background.setImageResource(R.drawable.layout_bg_paper);
                picture.setImageResource(R.drawable.layout_bg_album_sel);
            }
        });

        LayoutParams lps = new LayoutParams(99, 114);
        addBottomCenterView(background, lps);
        addBottomCenterView(picture, lps);

        changeBackgroundFrament = new ChangeBackgroundFragment();
        changePictureFragment = new ChangePictureFragment();

        changeBackgroundFrament.setData(cipai, writing);
        changePictureFragment.setData(cipai, writing);

        fragments.add(changeBackgroundFrament);
        fragments.add(changePictureFragment);
        changeFragment(currentIndex);
    }

    public void changeFragment(int pos)
    {
        currentIndex = pos;
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
