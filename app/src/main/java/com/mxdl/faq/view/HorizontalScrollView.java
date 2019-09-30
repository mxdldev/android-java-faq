package com.mxdl.faq.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Description: <自定义实现横向滚动的ViewPager><br>
 * Author:      mxdl<br>
 * Date:        2019/9/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class HorizontalScrollView extends ViewGroup {
    public HorizontalScrollView(Context context) {
        super(context);
    }

    public HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v("MYTAG","HorizontalScrollView start...");
    }

    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.v("MYTAG","widthMode:"+widthMode+";widthSize:"+widthSize);
        //Log.v("MYTAG","heightMode:"+heightMode);
        //Log.v("MYTAG","heightSize:"+heightSize);
        if(widthMode == MeasureSpec.UNSPECIFIED){
            Log.v("MYTAG","UNSPECIFIED");
        }else if(widthMode == MeasureSpec.EXACTLY){
            Log.v("MYTAG","EXACTLY");
        }else if(widthMode == MeasureSpec.AT_MOST){
            Log.v("MYTAG","AT_MOST");
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
