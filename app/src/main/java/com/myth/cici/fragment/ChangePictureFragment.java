package com.myth.cici.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.entity.Cipai;
import com.myth.cici.entity.ColorEntity;
import com.myth.cici.entity.Writing;
import com.myth.cici.util.Fastblur;
import com.myth.cici.util.ImageUtils;
import com.myth.cici.util.ResizeUtil;

public class ChangePictureFragment extends Fragment
{

    private static final int REQUEST_PICK_IMG = 0x8887;

    private Context mContext;

    private LinearLayout content;

    private TextView text;

    private Bitmap srcBitmap;

    private Bitmap destBitmap;

    private Cipai cipai;

    private Writing writing;

    private int bright = 127;

    private int radius = 0;

    private TextView title;

    public ChangePictureFragment()
    {
    }

    public static ChangePictureFragment getInstance(Cipai cipai, Writing writing)
    {
        ChangePictureFragment fileViewFragment = new ChangePictureFragment();
        fileViewFragment.cipai = cipai;
        fileViewFragment.writing = writing;
        return fileViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_picture, null);
        initViews(view);
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        refresh();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        save();
    }

    public void save()
    {
        writing.setBitmap(destBitmap);
        writing.setBgimg("");
    }

    private void refresh()
    {
        text.setText(writing.getText());
        content.setBackgroundDrawable(new BitmapDrawable(getResources(), destBitmap));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMG)
        {
            if (resultCode == Activity.RESULT_OK && data != null)
            {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = ((Activity) mContext).getContentResolver().query(selectedImage, filePathColumn, null,
                        null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bmp = ImageUtils.getimage(picturePath);
                srcBitmap = bmp;
                destBitmap = bmp;
            }
        }
    }

    private void initViews(View view)
    {
        content = (LinearLayout) view.findViewById(R.id.content);

        content.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_IMG);

            }
        });
        layoutItemContainer(content);
        title = (TextView) view.findViewById(R.id.title);
        title.setText(cipai.getName());
        text = (TextView) view.findViewById(R.id.text);
        title.setTypeface(MyApplication.getTypeface());
        text.setTypeface(MyApplication.getTypeface());

        setTextSize();
        setGravity();
        setPadding();
        setColor();

        // srcBitmap = BitmapFactory.decodeFile(pathName);
        if (destBitmap == null)
        {
            srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zuibaichi);
            destBitmap = srcBitmap;
        }

        SeekBar seekBar1 = (SeekBar) view.findViewById(R.id.seekBar1);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                drawPicture(bright, radius);
            }

            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                bright = progress;

            }
        });

        SeekBar seekBar2 = (SeekBar) view.findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                drawPicture(bright, radius);
            }

            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                radius = progress;
            }
        });

    }

    @TargetApi(11)
    private void drawPicture(int bright, int radius)
    {
        Bitmap bmp = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Config.ARGB_8888);

        try
        {
            int brightness = bright - 127;
            ColorMatrix cMatrix = new ColorMatrix();
            cMatrix.set(new float[] {1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness,// 改变亮度
                    0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});

            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
            Canvas canvas = new Canvas(bmp);
            canvas.drawBitmap(srcBitmap, 0, 0, paint);

            if (Build.VERSION.SDK_INT > 16)
            {
                RenderScript rs = RenderScript.create(getActivity());
                Allocation overlayAlloc = Allocation.createFromBitmap(rs, bmp);
                ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
                blur.setInput(overlayAlloc);
                blur.setRadius(radius + 1);
                blur.forEach(overlayAlloc);
                overlayAlloc.copyTo(bmp);
                rs.destroy();
            }
            else
            {
                // 低版本的折衷处理方法
                bmp = Fastblur.fastblur(mContext, bmp, radius);
            }
        }
        catch (Exception e)
        {
            Log.e("ChangePicture", "drawPictureError");
        }

        destBitmap = bmp;
        content.setBackgroundDrawable(new BitmapDrawable(getResources(), bmp));
    }

    private void layoutItemContainer(View itemContainer)
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemContainer.getLayoutParams();
        params.width = ResizeUtil.resize(mContext, 720);
        params.height = ResizeUtil.resize(mContext, 720);
        itemContainer.setLayoutParams(params);
    }

    private void setPadding()
    {
        int margin = MyApplication.getDefaultSharePadding(mContext);
        LinearLayout.LayoutParams lps = (android.widget.LinearLayout.LayoutParams) text.getLayoutParams();
        lps.leftMargin = margin;
        text.setLayoutParams(lps);
    }

    private void setGravity()
    {
        boolean isCenter = MyApplication.getDefaultShareGravity(mContext);
        if (isCenter)
        {
            text.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        else
        {
            text.setGravity(Gravity.LEFT);
        }
    }

    private void setTextSize()
    {
        int size = MyApplication.getDefaultShareSize(mContext);
        text.setTextSize(size);
        title.setTextSize(size + 2);
    }

    private void setColor()
    {

        ColorEntity colorEntity = MyApplication.getColorByPos(MyApplication.getDefaultShareColor(mContext));
        int color = Color.rgb(0, 0, 0);
        if (colorEntity != null)
        {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        }
        text.setTextColor(color);
        title.setTextColor(color);
    }
}
