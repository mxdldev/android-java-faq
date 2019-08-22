package com.mxdl.faq.android;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

/**
 * Description: <MyService><br>
 * Author:      mxdl<br>
 * Date:        2019/8/21<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Context context;
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
