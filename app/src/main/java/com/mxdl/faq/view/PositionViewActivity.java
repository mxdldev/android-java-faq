package com.mxdl.faq.view;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.mxdl.faq.R;

public class PositionViewActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTxtContent;
    private Button mBtnTest;
    private Button mBtnMove;
    private ViewGroup mViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_view);
        mTxtContent = findViewById(R.id.btn_content);
        mBtnTest = findViewById(R.id.btn_test);
        mBtnMove = findViewById(R.id.btn_move);
        mBtnTest.setOnClickListener(this);
        mBtnMove.setOnClickListener(this);

        mTxtContent.post(new Runnable() {
            @Override
            public void run() {
                test();
            }
        });

        ViewTreeObserver viewTreeObserver = mTxtContent.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheMemory = maxMemory/8;
        LruCache<String,String> lruCache = new LruCache<>(cacheMemory);
        lruCache.put("a","a");
        lruCache.remove("a");
        String a = lruCache.get("a");
        //DiskLruCache diskLruCache;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.v("MYTAG","onWindowFocusChanged start:"+hasFocus);
        if(hasFocus){
            test();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                test();
                break;
            case R.id.btn_move:
                ObjectAnimator.ofFloat(mTxtContent,View.TRANSLATION_X,50).setDuration(1000).start();
                ObjectAnimator.ofFloat(mTxtContent,View.TRANSLATION_Y,100).setDuration(1000).start();
                break;
        }
    }

    private void test() {
        Log.v("MYTAG", "left:" + mTxtContent.getLeft() + ";top:" + mTxtContent.getTop() + ";right:" + mTxtContent.getRight() + ";bottom:" + mTxtContent.getBottom());
        Log.v("MYTAG", "x:" + mTxtContent.getX() + ";y:" + mTxtContent.getY());
        Log.v("MYTAG", "transX:" + mTxtContent.getTranslationX() + ";transY:" + mTxtContent.getTranslationY());
        Log.v("MYTAG", "========================================");
    }
}
