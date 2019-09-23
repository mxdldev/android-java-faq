package com.mxdl.faq.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;

import java.lang.ref.WeakReference;

/**
 * Description: <Test><br>
 * Author:      mxdl<br>
 * Date:        2019/9/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class Test implements A,B{

    @Override
    public void test() {
    }
    public WeakReference<String> a(){
        String str = new String("123");
        WeakReference<String> weakReference = new WeakReference<>(str);
        return weakReference;
    }
    public void b(){
        WeakReference<String> b = a();
        String s = b.get().toUpperCase();
        System.out.println(s);
    }
    public static void main(String[] args){
        Test test = new Test();
        test.b();
    }
}
