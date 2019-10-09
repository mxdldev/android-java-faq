package com.mxdl.faq.art;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mxdl.faq.R;
import com.mxdl.faq.art.adapter.MyExpandableListViewAdapter;
import com.mxdl.faq.art.view1.MyPinnedHeaderExpandableListView;
import com.mxdl.faq.art.view1.MyStickyLayout;

/**
 * Description: <PinnedHeaderExpandableListViewActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/7<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class PinnedHeaderExpandableListViewActivity extends AppCompatActivity {

    private MyPinnedHeaderExpandableListView mExpandableListView;
    private MyStickyLayout mStickyLayout;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_header_expandable_list_view);
        mExpandableListView = findViewById(R.id.view_expand_listview);
        mExpandableListView.setAdapter(new MyExpandableListViewAdapter(this));
        for(int i = 0; i < 3; i++){
            mExpandableListView.expandGroup(i);
        }
        mExpandableListView.showPinnedHeaderView();

        mStickyLayout = findViewById(R.id.view_sticky_layout);
        mStickyLayout.setGiveUpTouchEventListener(new MyStickyLayout.GiveUpTouchEventListener() {
            @Override
            public boolean giveUpTouchEvent() {
                int firstVisiblePosition = mExpandableListView.getFirstVisiblePosition();
                if(firstVisiblePosition == 0){
                    View view = mExpandableListView.getChildAt(0);
                    if(view != null && view.getTop() >= 0){
                        return true;
                    }
                }
                return false;
            }
        });
    }


}
