package com.myth.cici.wiget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;

public class PasteEditText extends EditText {

    public PasteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public PasteEditText(Context context) {
        super(context);
    }

    private static final int ID_PASTE = android.R.id.paste;

    public interface OnPasteListener {

        void onPasteClick(int pos);
    }

    public OnPasteListener onPasteListener;
    
    public int line;

    @Override
    public boolean onTextContextMenuItem(int id) {

        if (id == ID_PASTE) {
            if (onPasteListener != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onPasteListener.onPasteClick(line);
                    }
                }, 200);
            }
        }
        return super.onTextContextMenuItem(id);
    }

    public OnPasteListener getOnPasteListener() {
        return onPasteListener;
    }

    public void setOnPasteListener(OnPasteListener onPasteListener) {
        this.onPasteListener = onPasteListener;
    }

}
