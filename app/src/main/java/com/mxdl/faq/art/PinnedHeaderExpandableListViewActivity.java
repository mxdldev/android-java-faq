package com.mxdl.faq.art;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.mxdl.faq.R;
import com.mxdl.faq.art.adapter.MyExpandableListViewAdapter;
import com.mxdl.faq.art.entity.Group;
import com.mxdl.faq.art.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: <PinnedHeaderExpandableListViewActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/7<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class PinnedHeaderExpandableListViewActivity extends AppCompatActivity {

    private ExpandableListView mExpandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_header_expandable_list_view);
        mExpandableListView = findViewById(R.id.view_expand_listview);

        mExpandableListView.setAdapter(new MyExpandableListViewAdapter(this));
    }


}
