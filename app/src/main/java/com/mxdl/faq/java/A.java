package com.mxdl.faq.java;

import java.util.concurrent.Callable;
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
public class A {
    int a;
    public A(){}
    public A(int a){
        this.a = a;
    }
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
    }
}
