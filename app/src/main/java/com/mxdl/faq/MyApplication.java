package com.mxdl.faq;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;


/**
 * Description: <MyApplication><br>
 * Author:      mxdl<br>
 * Date:        2019/8/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyApplication extends Application {
    public static boolean IS_DEBUG = true;
    @Override
    public void onCreate() {
        super.onCreate();
        if (IS_DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }
}
