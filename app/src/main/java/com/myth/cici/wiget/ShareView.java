package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.entity.Writing;
import com.myth.cici.util.ResizeUtil;
import com.myth.cici.util.StringUtils;


public class ShareView extends ScrollView {

    private Context mContext;

    private Writing writing;

    private TextView text;

    private TextView title;

    private TextView author;

    public MirrorLoaderView getBackgroundView() {
        return backgroundView;
    }

    private MirrorLoaderView backgroundView;

    public ImageView getPictureView() {
        return pictureView;
    }

    private ImageView pictureView;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_BACKGROUND = 1;
    public static final int TYPE_PICTURE = 2;


    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public ShareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView();
    }

    public ShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ShareView(Context context, Writing writing) {
        super(context);
        mContext = context;
        initView();
        setWriting(writing);
    }


    public void setWriting(Writing writing) {
        this.writing = writing;
        if (writing.getCipai() == null) {
            writing.setCipai(CipaiDatabaseHelper.getCipaiById(writing.getCi_id()));
        }
        refresh();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_share, this);

        backgroundView = (MirrorLoaderView) findViewById(R.id.background_view);
        pictureView = (ImageView) findViewById(R.id.picture_view);

        backgroundView.setMinimumHeight(ResizeUtil.getInstance().resize(540));
        pictureView.setMinimumHeight(ResizeUtil.getInstance().resize(540));

        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        author = (TextView) findViewById(R.id.author);


        title.setTypeface(MyApplication.instance.getTypeface());
        text.setTypeface(MyApplication.instance.getTypeface());
        author.setTypeface(MyApplication.instance.getTypeface());
    }

    public void refresh() {
        if (type == TYPE_NORMAL) {
            if (StringUtils.isNumeric(writing.getBgimg())) {
                backgroundView.setDrawableId(MyApplication.bgimgList[Integer.parseInt(writing.getBgimg())]);
            } else {
                pictureView.setImageDrawable(new BitmapDrawable(getResources(), writing.getBgimg()));
            }
        } else if (type == TYPE_BACKGROUND) {
            if (StringUtils.isNumeric(writing.getBgimg())) {
                backgroundView.setDrawableId(MyApplication.bgimgList[Integer.parseInt(writing.getBgimg())]);
            } else {
                backgroundView.setDrawableId(MyApplication.bgimgList[0]);
            }
        } else {
            if (TextUtils.isEmpty(writing.getBgimg())) {
                pictureView.setImageResource(R.drawable.zuibaichi);
            } else {
                pictureView.setImageDrawable(new BitmapDrawable(getResources(), writing.getBgimg()));
            }
        }

        text.setText(writing.getText());
        title.setText(writing.getCipai().getName());

        author.setText(MyApplication.instance.getDefaultUserName(mContext));

        setTextSize();
        setGravity();
        setPadding();
        setColor();
        setAuthor();

    }


    public void setPadding() {
        int margin = MyApplication.getDefaultSharePadding(mContext);
        LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) text.getLayoutParams();
        lps.leftMargin = margin;
        text.setLayoutParams(lps);
    }

    public void setGravity() {
        boolean isCenter = MyApplication.getDefaultShareGravity(mContext);
        if (isCenter) {
            text.setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            text.setGravity(Gravity.LEFT);
        }
    }

    public void setTextSize() {
        int size = MyApplication.getDefaultShareSize(mContext);
        text.setTextSize(size);
        title.setTextSize(size + 2);
        author.setTextSize(size - 2);
    }

    public void setColor() {

        ColorEntity colorEntity = MyApplication.getColorByPos(MyApplication.getDefaultShareColor(mContext));
        int color ;
        if (colorEntity != null) {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        } else {
            color = Color.rgb(0, 0, 0);
        }
        text.setTextColor(color);
        title.setTextColor(color);
        author.setTextColor(color);
    }

    public void setAuthor() {
        if (MyApplication.getDefaultShareAuthor(mContext)) {
            author.setVisibility(View.VISIBLE);
        } else {
            author.setVisibility(View.GONE);
        }
    }

}
