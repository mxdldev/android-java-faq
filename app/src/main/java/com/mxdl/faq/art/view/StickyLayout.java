package com.mxdl.faq.art.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Description: <StickyLayout><br>
 * Author:      mxdl<br>
 * Date:        2019/10/8<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class StickyLayout extends LinearLayout {

    private View mHeaderView;
    private int mOriginHeaderHeight;
    private int mCurrHeaderHeight;

    int mLastInterceptX = 0;
    int mLastInterceptY = 0;
    int mLastX = 0;
    int mLastY = 0;
    private int mStatus = STATUS_EXPANDED;
    public static final int STATUS_EXPANDED = 1;
    public static final int STATUS_COLLAPSED = 2;
    int mTouchSlop;
    private GiveUpTouchEventListener mGiveUpTouchEventListener;

    public void setGiveUpTouchEventListener(GiveUpTouchEventListener giveUpTouchEventListener) {
        mGiveUpTouchEventListener = giveUpTouchEventListener;
    }

    public interface GiveUpTouchEventListener {
        boolean giveUpTouchEvent();
    }

    public StickyLayout(Context context) {
        super(context);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && (mHeaderView == null)) {
            initView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initView() {
        int headerId = getResources().getIdentifier("sticky_header", "id", getContext().getPackageName());
        if (headerId != 0 ) {
            mHeaderView = findViewById(headerId);
            mOriginHeaderHeight = mHeaderView.getMeasuredHeight();
            mCurrHeaderHeight = mOriginHeaderHeight;
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                mLastX = x;
                mLastY = y;
                mLastInterceptX = x;
                mLastInterceptY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastInterceptX;
                int dy = y - mLastInterceptY;
                Log.v("MYTAG1", "ACTION_MOVE y:" + y + ";" + mCurrHeaderHeight);
                if (y <= mCurrHeaderHeight) {
                    intercept = false;
                    Log.v("MYTAG1", "case 1");
                } else if (Math.abs(dy) < Math.abs(dx)) {
                    Log.v("MYTAG1", "case 2");
                    intercept = false;
                } else if (mStatus == STATUS_EXPANDED && dy <= -mTouchSlop) {
                    Log.v("MYTAG1", "case 3");
                    intercept = true;
                } else if (mGiveUpTouchEventListener != null && mGiveUpTouchEventListener.giveUpTouchEvent() && dy >= mTouchSlop) {
                    Log.v("MYTAG1", "case 4");
                    intercept = true;
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                mCurrHeaderHeight += dy;
                setHeaderHeight(mCurrHeaderHeight);
                break;
            case MotionEvent.ACTION_UP:
                int destHeight = 0;
                if (mCurrHeaderHeight <= mOriginHeaderHeight * 0.5) {
                    destHeight = 0;
                    mStatus = STATUS_COLLAPSED;
                } else {
                    destHeight = mOriginHeaderHeight;
                    mStatus = STATUS_EXPANDED;
                }
                smoothSetHeaderHeight(mCurrHeaderHeight,destHeight,500);
                //smoothSetHeaderHeight1(mCurrHeaderHeight, destHeight, 500);
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(event);
    }


    private void setHeaderHeight(int height) {
        Log.v("MYTAG1", "mCurrHeaderHeight:" + height);

        if (height < 0) {
            height = 0;
        } else if (height >= mOriginHeaderHeight) {
            height = mOriginHeaderHeight;
        }
        if (height == 0) {
            mStatus = STATUS_COLLAPSED;
        } else {
            mStatus = STATUS_EXPANDED;
        }

        mHeaderView.getLayoutParams().height = height;
        mHeaderView.requestLayout();
    }

    public void smoothSetHeaderHeight1(final int from, final int to, int duration) {
        final int framecount = duration / 1000 * 30 + 1;
        final float partation = (to - from) / (float)framecount;
        new Thread("smoothSetheaderHeight") {
            @Override
            public void run() {
                for (int i = 0; i < framecount; i++) {
                    final int height;
                    if (i == framecount - 1) {
                        height = to;
                    }else{
                        height = (int)(from + partation * i);
                    }
                    post(new Runnable() {
                        @Override
                        public void run() {
                            setHeaderHeight(height);
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void smoothSetHeaderHeight(int from, int to, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to).setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                Log.v("MYTAG1", "value:" + value + ":" + Thread.currentThread().getName());
                setHeaderHeight(value);
            }
        });
        valueAnimator.start();
    }
}
