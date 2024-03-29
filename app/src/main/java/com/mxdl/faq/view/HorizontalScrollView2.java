package com.mxdl.faq.view;

import android.content.Context;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Description: <横向滚动的ScorllView><br>
 * Author:      mxdl<br>
 * Date:        2019/10/3<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 *     要求：
 *     有n个能上下滚动的RecycleView，要求他们横向摆放，而且能够左右滑动切换
 *     思路：
 *     很明显这是一个自定义的ViewGroup，使用ViewPager很轻松能实现这个效果，
 *     我们需要自定义实现这个View，以加深对ViewPager的理解，它既不是组合View，也不是自绘View
 *     而且是一个继承View，它有很多的子View，很显然他是一个ViewGruop
 *     1.确定控件的大小，它的大小由子View的大小所决定的
 *     2.确定控件的位置，主要不是确定他自身的位置，而是确定子控件的位置横向一字排开
 *     3.过滤拦截自已所需要的事件，左滑右滑事件
 *     4.消费事件a.实现左滑和右滑,b.松手后，如果小于一半则回弹，如果大于一半则切换到下一页面
 */
public class HorizontalScrollView2 extends ViewGroup {

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mLastX = 0;
    private int mLastY = 0;
    private int mChildViewWidth;
    private int mChildIndex;

    public HorizontalScrollView2(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalScrollView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int mChildCount = getChildCount();

        if (mChildCount == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        View childView = getChildAt(0);
        if (widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(childView.getMeasuredWidth() * mChildCount, childView.getMeasuredHeight());
        } else if (widthMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(childView.getMeasuredWidth() * mChildCount, heightMeasureSize);
        } else if (heightMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSize, childView.getMeasuredHeight());
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childLeft = 0;
        int mChildCount = getChildCount();
        for (int j = 0; j < mChildCount; j++) {
            View childView = getChildAt(j);
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();
            mChildViewWidth = width;
            childView.layout(childLeft, 0, childLeft + width, height);
            childLeft += width;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mLastX = x;
            mLastY = y;
            if(!mScroller.isFinished()){
                mScroller.abortAnimation();
                intercept = true;
            }else{
                intercept = false;
            }
        } else {
            intercept = true;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    scrollBy(-dx, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                int mChildCount = getChildCount();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildViewWidth / 2) / mChildViewWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildCount - 1));
                int deltaX = mChildViewWidth * mChildIndex - scrollX;
                Log.v("MYTAG", "mChildIndex:" + mChildIndex+":dx:"+deltaX);
                smoothScrollTo(deltaX,0);
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}
