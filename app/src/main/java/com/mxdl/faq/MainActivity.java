package com.mxdl.faq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.PopupWindow;

/**
 * Description: <MainActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/8/19<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplication().getApplicationContext().startActivities();
    }
}
