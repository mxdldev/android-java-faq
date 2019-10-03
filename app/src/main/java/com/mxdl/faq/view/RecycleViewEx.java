package com.mxdl.faq.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description: <RecycleViewEx><br>
 * Author:      mxdl<br>
 * Date:        2019/10/3<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class RecycleViewEx extends RecyclerView {
    private HorizontalScrollView2 mHorizontalScrollView2;
    private int mLastX = 0;
    private int mLastY = 0;
    public RecycleViewEx(@NonNull Context context) {
        super(context);
    }

    public RecycleViewEx(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecycleViewEx(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHorizontalScrollView2(HorizontalScrollView2 horizontalScrollView2) {
        mHorizontalScrollView2 = horizontalScrollView2;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mHorizontalScrollView2.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                if(Math.abs(dx) > Math.abs(dy)){
                    mHorizontalScrollView2.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
