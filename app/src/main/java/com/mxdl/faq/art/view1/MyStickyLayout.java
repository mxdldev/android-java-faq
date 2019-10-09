package com.mxdl.faq.art.view1;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Description: <StickyLayout><br>
 * Author:      mxdl<br>
 * Date:        2019/10/9<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 *     思路：
 *     1.过滤要用到的move事件
 *     2.消费move事件
 *     3.消费up事件
 */
public class MyStickyLayout extends LinearLayout {

    private View mHeaderView;
    private int mOriginHeaderHeight;
    private int mCurrHeaderHeight;
    private int mScaledTouchSlop;
    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastX;
    private int mLastY;
    private int mSateExpand = 0;
    private int mStateCollapsed = 1;
    private int mState = mSateExpand;
    private GiveUpTouchEventListener mGiveUpTouchEventListener;

    public void setGiveUpTouchEventListener(GiveUpTouchEventListener giveUpTouchEventListener) {
        mGiveUpTouchEventListener = giveUpTouchEventListener;
    }

    public interface GiveUpTouchEventListener{
        boolean giveUpTouchEvent();
    }
    public MyStickyLayout(Context context) {
        super(context);
    }

    public MyStickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyStickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyStickyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            int headerId = getResources().getIdentifier("sticky_header", "id", getContext().getPackageName());
            mHeaderView = findViewById(headerId);
            mOriginHeaderHeight = mHeaderView.getMeasuredHeight();
            mCurrHeaderHeight = mOriginHeaderHeight;
            mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                mLastInterceptX = x;
                mLastInterceptY = y;
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastInterceptX;
                int dy = y - mLastInterceptY;
                if(y <= mCurrHeaderHeight){
                    intercept = false;
                }else if(Math.abs(dx) > Math.abs(dy)){
                    intercept = false;
                }else if(mState == mSateExpand && dy < - mScaledTouchSlop){
                    intercept = true;
                }else if(mGiveUpTouchEventListener != null && mGiveUpTouchEventListener.giveUpTouchEvent() && dy > mScaledTouchSlop){
                    intercept= true;
                }else{
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                mLastInterceptX = 0;
                mLastInterceptY = 0;
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - mLastY;
                mCurrHeaderHeight += dy;
                setHeaderHeight(mCurrHeaderHeight);
                break;
            case MotionEvent.ACTION_UP:
                int dest;
                if(mCurrHeaderHeight < mOriginHeaderHeight * 0.5){
                    dest = 0;
                    mState = mStateCollapsed;
                }else{
                    dest = mOriginHeaderHeight;
                    mState = mSateExpand;
                }
                smoothSetHeaderHeight(mCurrHeaderHeight, dest, 500);
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothSetHeaderHeight(int from, int to, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to).setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setHeaderHeight((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private void setHeaderHeight(int height) {
        if(height <= 0){
            height = 0;
        }else if(height > mOriginHeaderHeight){
            height = mOriginHeaderHeight;
        }
        if(height == 0){
            mState = mStateCollapsed;
        }else{
            mState = mSateExpand;
        }
        mHeaderView.getLayoutParams().height = height;
        mHeaderView.requestLayout();
        mCurrHeaderHeight = height;
    }
}
