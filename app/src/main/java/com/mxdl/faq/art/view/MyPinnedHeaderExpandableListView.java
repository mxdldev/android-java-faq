package com.mxdl.faq.art.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mxdl.faq.R;

/**
 * Description: <头部固定的ExpandableListView><br>
 * Author:      mxdl<br>
 * Date:        2019/10/8<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyPinnedHeaderExpandableListView extends ExpandableListView {

    private View mPinnedHeader;
    private int mPinnedHeaderWidth;
    private int mPinndeHeaderHeight;
    private TextView mTxtTitle;

    public MyPinnedHeaderExpandableListView(Context context) {
        super(context);
        initView();
    }

    public MyPinnedHeaderExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyPinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyPinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //Log.v("MYTAG", "onScrollStateChanged scrollState:" + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.v("MYTAG", "onScroll firstVisibleItem:" + firstVisibleItem + ";visibleItemCount:" + visibleItemCount + ";totalItemCount" + totalItemCount);
                Log.v("MYTAG","getFirstVisiblePosition:"+getFirstVisiblePosition());
                refreshHeader();
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mPinnedHeader == null) {
            return;
        }
        measureChild(mPinnedHeader, widthMeasureSpec, heightMeasureSpec);
        mPinnedHeaderWidth = mPinnedHeader.getMeasuredWidth();
        mPinndeHeaderHeight = mPinnedHeader.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mPinnedHeader == null) {
            return;
        }
        mPinnedHeader.layout(0, 0, mPinnedHeaderWidth, mPinndeHeaderHeight);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mPinnedHeader == null) {
            return;
        }
        drawChild(canvas, mPinnedHeader, getDrawingTime());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (mPinnedHeader != null && y >= mPinnedHeader.getTop() && y <= mPinnedHeader.getBottom()) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                int position = pointToPosition(x, y);
                int positionGroup = getPackedPositionGroup(getExpandableListPosition(position));
                if(positionGroup != AdapterView.INVALID_POSITION){
                    if (isGroupExpanded(positionGroup)) {
                        collapseGroup(positionGroup);
                    } else {
                         expandGroup(positionGroup);
                    }
                }
            }
            return true;
        }

        return super.dispatchTouchEvent(ev);

    }

    public void showPinnedHeaderView() {
        mPinnedHeader = LayoutInflater.from(getContext()).inflate(R.layout.group, null);
        mPinnedHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mTxtTitle = mPinnedHeader.findViewById(R.id.group);
        mTxtTitle.setText("group-0");
        mPinnedHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hello world", Toast.LENGTH_LONG);
            }
        });
        requestLayout();
        postInvalidate();
    }

    private void refreshHeader() {
        if (mPinnedHeader == null) {
            return;
        }
        int firstVisiblePosition = getFirstVisiblePosition();
        int firstGroupPositon = getPackedPositionGroup(getExpandableListPosition(firstVisiblePosition));

        int nextVisiblePosition = firstVisiblePosition + 1;
        int nextGroupPositon = getPackedPositionGroup(getExpandableListPosition(nextVisiblePosition));
        Log.v("MYTAG", "firstVisiblePosition:" + firstGroupPositon + ";firstGroupPositon:" + firstGroupPositon + ";nextVisiblePosition:" + nextVisiblePosition + ";nextGroupPositon:" + nextGroupPositon);
        View child = getChildAt(1);
        if (child == null) {
            return;
        }
        int top = child.getTop();
        if (firstGroupPositon + 1 == nextGroupPositon) {
            Log.v("MYTAG", "two group love...");
            if (top <= mPinndeHeaderHeight) {
                int delta = mPinndeHeaderHeight - top;
                mPinnedHeader.layout(0, -delta, mPinnedHeaderWidth, mPinndeHeaderHeight - delta);
            } else {
                mPinnedHeader.layout(0, 0, mPinnedHeaderWidth, mPinndeHeaderHeight);
            }

        } else {
            mPinnedHeader.layout(0, 0, mPinnedHeaderWidth, mPinndeHeaderHeight);
        }
        mTxtTitle.setText("group-" + firstGroupPositon);
    }
}
