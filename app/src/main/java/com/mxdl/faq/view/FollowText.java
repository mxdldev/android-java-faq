package com.mxdl.faq.view;

import com.nineoldandroids.view.ViewHelper;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FollowText extends TextView {
    private static final String TAG = "TestButton";
    private int mScaledTouchSlop;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public FollowText(Context context) {
        this(context, null);
    }

    public FollowText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FollowText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext())
                .getScaledTouchSlop();
        Log.d(TAG, "sts:" + mScaledTouchSlop);
    }

    @SuppressLint({"ClickableViewAccessibility", "ObjectAnimatorBinding"})
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            Log.d(TAG, "down, currX:" + x + " currX:" + y +";mLastX:" + mLastX + " mLastY:" + mLastY);
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            Log.d(TAG, "move, currX:" + x + " currX:" + y +";mLastX:" + mLastX + " mLastY:" + mLastY+";deltaX:" + deltaX + " deltaY:" + deltaY);
//            int translationX = (int)ViewHelper.getTranslationX(this) + deltaX;
//            int translationY = (int)ViewHelper.getTranslationY(this) + deltaY;
//            ViewHelper.setTranslationX(this, translationX);
//            ViewHelper.setTranslationY(this, translationY);

//            ObjectAnimator.ofFloat(this, View.X,x).setDuration(0).start();
//            ObjectAnimator.ofFloat(this,View.Y,y).setDuration(0).start();
//            ObjectAnimator.ofFloat(this, View.TRANSLATION_X,deltaX).setDuration(0).start();
//            ObjectAnimator.ofFloat(this,View.TRANSLATION_Y,deltaY).setDuration(0).start();
            scrollBy(-deltaX,0);
            break;
        }
        case MotionEvent.ACTION_UP: {
            Log.d(TAG, "up, currX:" + x + " currX:" + y +";mLastX:" + mLastX + " mLastY:" + mLastY);
            break;
        }
        default:
            break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

}
