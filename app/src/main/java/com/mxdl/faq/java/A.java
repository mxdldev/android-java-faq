package com.mxdl.faq.java;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.util.SimpleArrayMap;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Description: <A><br>
 * Author:      mxdl<br>
 * Date:        2019/8/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class A extends Service {
    int a;
    public A(){}

    @Override
    public void onCreate() {
        super.onCreate();
        //startForegroundService(new Intent(this,));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public A(int a){
        this.a = a;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void test(){
        int a = 0;
        int c = 1 + a;
        Thread thread = new Thread();
        thread.interrupt();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        FutureTask<Integer> ft = new FutureTask<Integer>(new Callable(){
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

        new Thread(ft).start();

        CopyOnWriteArrayList<String> map = new CopyOnWriteArrayList<>();
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet();
        ConcurrentHashMap hashMap = new ConcurrentHashMap();
        //hashMap.put()
        //hashMap.put("")
        set.add("fafafa");
        set.add("1");
        set.add("2");
        set.add("3");
        map.add("fadfs");

        SimpleArrayMap arrayMap = new SimpleArrayMap();
        ArrayMap h = new ArrayMap();
        Set set2 = h.entrySet();
        Set set1 = set2;
        h.put("aaa","bbb");
        h.put("bbb","aaa");

        SparseArray array = new SparseArray();
        array.put(1,"1");

        SparseBooleanArray array1 = new SparseBooleanArray();
        array1.put(1,true);
        array1.put(2,false);
        ConcurrentLinkedQueue linkedDeque = new ConcurrentLinkedQueue<>();
        linkedDeque.add("aaa");
    }
}
