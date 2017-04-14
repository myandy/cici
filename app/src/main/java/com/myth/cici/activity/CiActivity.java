package com.myth.cici.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myth.cici.BaseActivity;
import com.myth.cici.R;
import com.myth.cici.db.CiDatabaseHelper;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Ci;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.util.DisplayUtil;
import com.myth.cici.util.OthersUtils;
import com.myth.cici.wiget.CircleEditView;
import com.myth.cici.wiget.TouchEffectImageView;

import java.util.ArrayList;
import java.util.Random;

public class CiActivity extends BaseActivity {

    private ArrayList<Ci> ciList = new ArrayList<Ci>();

    private Cipai cipai;

    private int num;

    private TextView content;

    private boolean isIntroduce = false;

    private boolean isRandom = false;

    private Ci ci;

    private TextView title;

    private TextView title1;

    private CircleEditView editView;

    TouchEffectImageView prev;

    TouchEffectImageView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ci);

        if (getIntent().hasExtra("cipai")) {
            ciList = (ArrayList<Ci>) getIntent().getSerializableExtra("cilist");
            cipai = (Cipai) getIntent().getSerializableExtra("cipai");
            num = getIntent().getIntExtra("num", 0);
            if (num == 0) {
                isIntroduce = true;
            } else {
                ci = ciList.get(num);
            }
        } else {
            isRandom = true;
            ciList = CiDatabaseHelper.getAllCi();
            getRandomCi();
        }

        initView();
    }

    private void getRandomCi() {
        ci = ciList.get(new Random().nextInt(ciList.size()));
        cipai = CipaiDatabaseHelper.getCipaiById(ci.getCi_id());
        if (cipai.getParent_id() > 0) {
            Cipai cipai1 = CipaiDatabaseHelper.getCipaiById(cipai
                    .getParent_id());
            cipai.setColor_id(cipai1.getColor_id());
            cipai.setSource(cipai1.getSource());
        }
    }

    private void setColor() {
        ColorEntity colorEntity = myApplication.getColorById(cipai
                .getColor_id());
        int color = 0xffffff;
        if (colorEntity != null) {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(),
                    colorEntity.getBlue());
        }
        editView.setColor(color);
    }

    private void initView() {
        LinearLayout topView = (LinearLayout) findViewById(R.id.right);
        LayoutParams param = new LayoutParams(
                DisplayUtil.dip2px(mActivity, 80), DisplayUtil.dip2px(
                mActivity, 120));
        editView = new CircleEditView(mActivity);
        topView.addView(editView, 1, param);

        title = (TextView) findViewById(R.id.title);
        title.setTypeface(myApplication.getTypeface());


        title.setTextSize(44);

        title1 = (TextView) findViewById(R.id.title1);
        title1.setTypeface(myApplication.getTypeface());
        title1.setTextSize(44);


        content = (TextView) findViewById(R.id.content);
        content.setTypeface(myApplication.getTypeface());
        content.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity).setItems(
                        new String[]{"复制文本"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                if (which == 0) {
                                    OthersUtils.copy(title.getText() + "\n"
                                            + content.getText(), mActivity);
                                    Toast.makeText(mActivity,
                                            R.string.copy_text_done,
                                            Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }).show();

            }
        });
        ((TextView) findViewById(R.id.note)).setTypeface(myApplication
                .getTypeface());

        findViewById(R.id.note).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity).setItems(
                        new String[]{"复制文本"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                if (which == 0) {
                                    OthersUtils.copy(title.getText() + "\n"
                                            + content.getText(), mActivity);
                                    Toast.makeText(mActivity,
                                            R.string.copy_text_done,
                                            Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        ((TextView) findViewById(R.id.author)).setTypeface(myApplication
                .getTypeface());
        if (isIntroduce) {
            findViewById(R.id.share).setVisibility(View.GONE);
        } else {
            findViewById(R.id.share).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity,
                            ShareEditActivity.class);
                    ci.setCipai(cipai);
                    intent.putExtra("ci", ci);
                    startActivity(intent);
                }
            });
        }
        editView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, EditActivity.class);
                intent.putExtra("cipai", cipai);
                startActivity(intent);
            }
        });

        initBottomRightView();

        refreshRandomView();

    }

    private void initBottomRightView() {
        if (isIntroduce) {
            return;
        } else if (isRandom) {
            ImageView view = new TouchEffectImageView(mActivity, null);
            view.setImageResource(R.drawable.random);
            view.setScaleType(ScaleType.FIT_XY);
            view.setPadding(12, 12, 12, 12);
            addBottomRightView(view,
                    new LayoutParams(DisplayUtil.dip2px(mActivity, 30.4),
                            DisplayUtil.dip2px(mActivity, 24)));
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    getRandomCi();
                    refreshRandomView();
                }
            });
        } else {
            prev = new TouchEffectImageView(mActivity, null);
            prev.setImageResource(R.drawable.prev);
            prev.setScaleType(ScaleType.FIT_XY);
            addBottomRightView(prev,
                    new LayoutParams(DisplayUtil.dip2px(mActivity, 42),
                            DisplayUtil.dip2px(mActivity, 42)));
            prev.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (num > 1) {
                        num--;
                        ci = ciList.get(num);
                        initContentView();
                    }
                }
            });

            next = new TouchEffectImageView(mActivity, null);
            next.setImageResource(R.drawable.next);
            next.setScaleType(ScaleType.FIT_XY);
            addBottomRightView(next,
                    new LayoutParams(DisplayUtil.dip2px(mActivity, 42),
                            DisplayUtil.dip2px(mActivity, 42)));
            next.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (num < ciList.size() - 1) {
                        num++;
                        ci = ciList.get(num);
                        initContentView();
                    }

                }
            });
        }

    }

    private void refreshRandomView() {
        if (!TextUtils.isEmpty(cipai.getName())) {
            title.setText(cipai.getName());
            if (cipai.getName().length() > 5) {
                title1.setText(cipai.getName().substring(0, 5));
                title.setText(cipai.getName().substring(5));
            } else {
                title1.setText("");
            }
        }
        setColor();
        initContentView();
    }

    private void initContentView() {
        if (isIntroduce) {
            WebView intro = (WebView) findViewById(R.id.intro);
            intro.setBackgroundColor(0);
            intro.setVisibility(View.VISIBLE);
            WebSettings settings = intro.getSettings();
            settings.setDefaultTextEncodingName("UTF-8");
            intro.loadUrl("file:///android_asset/intro.html");
            content.setText(cipai.getSource());
        } else {
            if (!isRandom) {
                if (num < ciList.size() - 1) {
                    next.setClickEnable();
                } else {
                    next.setClickDisable();
                }
                if (num > 1) {
                    prev.setClickEnable();
                } else {
                    prev.setClickDisable();
                }
            }
            String note = ci.getNote();
            if (note == null) {
                note = "";
            }
            content.setText(ci.getText());
            ((TextView) findViewById(R.id.note)).setText(note);
            ((TextView) findViewById(R.id.author)).setText(ci.getAuthor()
                    + "\n");
        }
    }

}
