package com.myth.cici.wiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.myth.cici.R;

public class GCDialog extends Dialog
{

    public static final String DATA_TITLE = "title";

    public static final String DATA_CONTENT = "content";

    public static final String CANCEL_TEXT = "extra_cancel_text";

    public static final String CANCEL_ENABLED = "cancel";

    public static final String CONFIRM_TEXT = "extra_confirm_text";

    public static final String TOUCH_OUT_SIDE = "extra_touch_out_side";

    private View titleLayout;

    private TextView titleView;

    private TextView contentView;

    private TextView confirmView;

    private TextView cancelView;

    private View devideLine;

    Context context;

    private OnCustomDialogListener listener;

    private Bundle bundle;

    public interface OnCustomDialogListener
    {
        public void onConfirm();

        public void onCancel();
    }

    public GCDialog(Context context, OnCustomDialogListener listener, Bundle bundle)
    {
        super(context, R.style.Theme_NoTitleBarDialog);
        this.context = context;
        this.listener = listener;
        this.bundle = bundle;
    }

    public GCDialog(Context context, int theme)
    {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.simple_dialog_activity);

        titleLayout = findViewById(R.id.simple_dialog_title_layout);
        titleView = (TextView) findViewById(R.id.simple_dialog_title);
        confirmView = (TextView) findViewById(R.id.simple_dialog_confirm);

        cancelView = (TextView) findViewById(R.id.simple_dialog_cancel);
        contentView = (TextView) findViewById(R.id.simple_dialog_content);
        devideLine = findViewById(R.id.simple_dialog_divide_line);

        if (bundle != null)
        {
            String title = bundle.getString(DATA_TITLE);
            if (title != null)
            {
                // 标题
                titleLayout.setVisibility(View.VISIBLE);
                titleView.setText(title);
            }
            else
            {
                titleLayout.setVisibility(View.GONE);
            }
            String cancel = bundle.getString(CANCEL_TEXT);
            if (cancel != null)
            {
                // 取消
                cancelView.setText(cancel);
            }

            String content = bundle.getString(DATA_CONTENT);
            if (content != null)
            {
                contentView.setText(Html.fromHtml(content));
            }
            boolean cancelEnabled = bundle.getBoolean(CANCEL_ENABLED, true);
            if (!cancelEnabled)
            {
                // 隐藏取消按钮
                cancelView.setVisibility(View.GONE);
                devideLine.setVisibility(View.GONE);
            }

            String confirmText = bundle.getString(CONFIRM_TEXT);
            if (!TextUtils.isEmpty(confirmText))
            {
                confirmView.setText(confirmText);
            }
        }

        confirmView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onConfirm();
                dismiss();
            }
        });
        cancelView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onCancel();
                dismiss();
            }
        });
    }

}
