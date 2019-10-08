package com.mxdl.faq.art;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mxdl.faq.R;
import com.mxdl.faq.art.adapter.MyExpandableListViewAdapter;
import com.mxdl.faq.art.view.MyPinnedHeaderExpandableListView;

/**
 * Description: <PinnedHeaderExpandableListViewActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/7<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class PinnedHeaderExpandableListViewActivity extends AppCompatActivity {

    private MyPinnedHeaderExpandableListView mExpandableListView;


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
    }


}
