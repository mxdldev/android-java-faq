package com.mxdl.faq.view;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxdl.faq.R;

import java.util.Random;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private String[] colors = new String[]{"#FF0000","#00FF00","#0000FF"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                Log.v("MYTAG", "getCount start...");
                return 3;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                Log.v("MYTAG", "instantiateItem start...");
                TextView txtView = new TextView(container.getContext());
                txtView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                txtView.setText(String.valueOf(position));
                txtView.setGravity(Gravity.CENTER);
                txtView.setBackgroundColor(Color.parseColor(colors[position]));
                container.addView(txtView);
                return txtView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                Log.v("MYTAG", "destroyItem start..." );
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                boolean b = view == o;
                Log.v("MYTAG", "isViewFromObject start :" + b);
                return b;
            }
        });
    }
}
