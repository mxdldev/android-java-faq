package com.mxdl.faq.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Description: <TestService><br>
 * Author:      mxdl<br>
 * Date:        2019/9/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TestService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
