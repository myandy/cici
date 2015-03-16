package com.myth.cici.activity;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myth.cici.BaseActivity;
import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.Writing;
import com.myth.cici.util.FileUtils;
import com.myth.cici.util.OthersUtils;
import com.myth.cici.util.ResizeUtil;
import com.myth.cici.util.StringUtils;

public class ShareActivity extends BaseActivity
{

    private Cipai cipai;

    private Writing writing;

    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        setBottomGone();

        writing = (Writing) getIntent().getSerializableExtra("writing");
        if (writing != null)
        {
            cipai = writing.getCipai();
        }
        else
        {
            finish();
        }

        initView();
    }

    private void initView()
    {
        content = (LinearLayout) findViewById(R.id.content);
        final TextView title = (TextView) findViewById(R.id.title);
        final TextView text = (TextView) findViewById(R.id.text);
        content.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(mActivity).setItems(new String[] {"复制文本", "保存图片", "分享"},
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                if (which == 0)
                                {
                                    OthersUtils.copy(title.getText() + "\n" + text.getText(), mActivity);
                                    Toast.makeText(mActivity, R.string.copy_text_done, Toast.LENGTH_SHORT).show();
                                }
                                else if (which == 1)
                                {
                                    String filePath = saveImage();
                                    if (!TextUtils.isEmpty(filePath))
                                    {
                                        Toast.makeText(mActivity, "图片已保存在：" + filePath, Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else if (which == 2)
                                {
                                    String filePath = saveImage();
                                    if (!TextUtils.isEmpty(filePath))
                                    {
                                        OthersUtils.shareMsg(mActivity, "词Ci", "share", "content", filePath);
                                    }

                                }
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        layoutItemContainer(content);
        title.setText(cipai.getName());
        text.setText(writing.getText());
        title.setTypeface(MyApplication.typeface);
        text.setTypeface(MyApplication.typeface);

        if (StringUtils.isNumeric(writing.getBgimg()))
        {
            content.setBackgroundResource(MyApplication.bgimgList[Integer.parseInt(writing.getBgimg())]);
        }
        else if (writing.getBitmap() != null)
        {
            content.setBackground(new BitmapDrawable(getResources(), writing.getBitmap()));
        }
        else
        {
            content.setBackground(new BitmapDrawable(getResources(), writing.getBgimg()));
        }

        scaleRotateIn(content, 1000, 0);
    }

    private void layoutItemContainer(View itemContainer)
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemContainer.getLayoutParams();
        params.width = ResizeUtil.resize(mActivity, 600);
        params.height = ResizeUtil.resize(mActivity, 900);
        itemContainer.setLayoutParams(params);
    }

    public final int rela1 = Animation.RELATIVE_TO_SELF;

    public void scaleRotateIn(View view, long durationMillis, long delayMillis)
    {
        view.setVisibility(View.VISIBLE);
        ScaleAnimation animation1 = new ScaleAnimation(0, 1, 0, 1, rela1, 0.5f, rela1, 0.5f);
        RotateAnimation animation2 = new RotateAnimation(0, 354, rela1, 0.5f, rela1, 0.5f);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        animation.setFillAfter(true);
        animation.setDuration(durationMillis);
        animation.setStartOffset(delayMillis);
        view.setAnimation(animation);
    }

    private String saveImage()
    {
        String filePath = null;
        try
        {
            String filename = cipai.getEnname() + writing.getUpdate_dt();
            filePath = FileUtils.saveFile(OthersUtils.createViewBitmap(content), filename);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return filePath;
    }

}
