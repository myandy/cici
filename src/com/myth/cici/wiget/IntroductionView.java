package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.StackView;

import com.myth.cici.R;
import com.myth.cici.adapter.IntroAdapter;

public class IntroductionView extends RelativeLayout
{


    private Context mContext;

    final int[] mColors = {Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.RED};

    public IntroductionView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public IntroductionView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public IntroductionView(Context context)
    {
        super(context);
        mContext = context;
        initView();
    }

    private void initView()
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_intro, null);
        
        final StackView stackView = (StackView)root. findViewById(R.id.stack_view);  
        


        IntroAdapter colorAdapter = new IntroAdapter(mContext, mColors);
        stackView.setAdapter(colorAdapter);  
          
//        final Button previousButon = (Button) findViewById(R.id.previousButton);  
//        previousButon.setOnClickListener(new OnClickListener() {  
//            public void onClick(View view) {  
//                stackView.showPrevious();  
//            }  
//        });  
//          
//        final Button nextButton = (Button) findViewById(R.id.nextButton);  
//        nextButton.setOnClickListener(new OnClickListener() {  
//            public void onClick(View view) {  
//                stackView.showNext();  
//            }  
//        });  

        addView(root, new LayoutParams(-1, -1));
    }  
      


}
