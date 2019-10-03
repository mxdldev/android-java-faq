package com.mxdl.faq.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxdl.faq.R;

public class HorizontalScrollViewActivity2 extends AppCompatActivity {

    private HorizontalScrollView2 mHorizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view2);
        mHorizontalScrollView = findViewById(R.id.view_scroll_view);
        for (int i = 0; i < 3; i++) {
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item_rec_view2, mHorizontalScrollView, false);
            TextView txtTitle = viewGroup.findViewById(R.id.txt_title);
            txtTitle.setText(String.valueOf(i));
            RecycleViewEx recView = viewGroup.findViewById(R.id.recview_content);
            recView.setHorizontalScrollView2(mHorizontalScrollView);
            recView.setLayoutManager(new LinearLayoutManager(this));
            recView.setAdapter(new MyAdapter(this));
            mHorizontalScrollView.addView(viewGroup);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder>{

        private Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_text_view,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            Log.v("MYTAG","i:"+i);
            myHolder.txtContent.setText(String.valueOf(i));
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }
    class MyHolder extends RecyclerView.ViewHolder{
        public TextView txtContent;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_content);
        }
    }
}
