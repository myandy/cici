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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.myth.cici.R;
import com.myth.cici.entity.Writing;
import com.myth.cici.util.Fastblur;
import com.myth.cici.util.ImageUtils;
import com.myth.cici.util.ResizeUtil;
import com.myth.cici.wiget.ShareView;

public class ChangePictureFragment extends Fragment {

    private static final int REQUEST_PICK_IMG = 0x8887;

    private Context mContext;


    private Bitmap srcBitmap;

    private Bitmap destBitmap;

    private Writing writing;

    private int bright = 127;

    private int radius = 0;

    private ShareView shareView;


    public ChangePictureFragment() {
    }

    public static ChangePictureFragment getInstance(Writing writing) {
        ChangePictureFragment fileViewFragment = new ChangePictureFragment();
        fileViewFragment.writing = writing;
        return fileViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        save();
    }

    public void save() {
        writing.setBitmap(destBitmap);
        writing.setBgimg("");
    }

    private void refresh() {
        shareView.setWriting(writing);
        shareView.getPictureView().setImageDrawable(new BitmapDrawable(getResources(), destBitmap));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMG) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = (mContext).getContentResolver().query(selectedImage, filePathColumn, null,
                        null, null);

                if (cursor != null) {
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
    }

    private void initViews(View view) {
        shareView = (ShareView) view.findViewById(R.id.share_view);
        ResizeUtil.getInstance().layoutSquareView(shareView);
        shareView.setType(ShareView.TYPE_PICTURE);
        shareView.setWriting(writing);

        View contentLL = view.findViewById(R.id.content_linear);
        contentLL.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_IMG);

            }
        });

        // srcBitmap = BitmapFactory.decodeFile(pathName);
        if (srcBitmap == null) {
            srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zuibaichi);
            destBitmap = srcBitmap;
        }

        SeekBar seekBar1 = (SeekBar) view.findViewById(R.id.seekBar1);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                drawPicture(bright, radius);
            }

            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bright = progress;

            }
        });

        SeekBar seekBar2 = (SeekBar) view.findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                drawPicture(bright, radius);
            }

            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radius = progress;
            }
        });

    }

    @TargetApi(11)
    private void drawPicture(int bright, int radius) {
        Bitmap bmp = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Config.ARGB_8888);

        try {
            int brightness = bright - 127;
            ColorMatrix cMatrix = new ColorMatrix();
            cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness,// 改变亮度
                    0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});

            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
            Canvas canvas = new Canvas(bmp);
            canvas.drawBitmap(srcBitmap, 0, 0, paint);

            if (Build.VERSION.SDK_INT > 16) {
                RenderScript rs = RenderScript.create(getActivity());
                Allocation overlayAlloc = Allocation.createFromBitmap(rs, bmp);
                ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
                blur.setInput(overlayAlloc);
                blur.setRadius(radius + 1);
                blur.forEach(overlayAlloc);
                overlayAlloc.copyTo(bmp);
                rs.destroy();
            } else {
                // 低版本的折衷处理方法
                bmp = Fastblur.fastblur(mContext, bmp, radius);
            }
        } catch (Exception e) {
            Log.e("ChangePicture", "drawPictureError");
        }

        destBitmap = bmp;
        return;
    }


}
