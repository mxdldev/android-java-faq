package com.mxdl.faq;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.mxdl.faq.preformance.StrictModeActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import hugo.weaving.DebugLog;
import io.reactivex.functions.Consumer;

/**
 * Description: <MainActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/8/19<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStrictMode;
    private Button mBtnHuGo;
    private Button mBtnHandlerLeak;
    private MyHandler mMyHandler;
    private Button mBtnMemoryShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStrictMode = findViewById(R.id.btn_strick_mode);
        mBtnHuGo = findViewById(R.id.btn_hugo);
        mBtnHandlerLeak = findViewById(R.id.btn_handelr_leak);

        mBtnMemoryShake = findViewById(R.id.btn_memory_shake);

        mBtnStrictMode.setOnClickListener(this);
        mBtnHuGo.setOnClickListener(this);
        mBtnHandlerLeak.setOnClickListener(this);
        mBtnMemoryShake.setOnClickListener(this);


        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    Toast.makeText(MainActivity.this, "没有相关权限", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_strick_mode:
                writeFile();
                break;
            case R.id.btn_hugo:
                String name = getName("zhang", "san");
                Log.v("MYTAG","name:"+name);
                break;
            case R.id.btn_handelr_leak:
                handlerLeak();
                break;
            case R.id.btn_memory_shake:
                memoryShake();
                break;
        }
    }

    private void memoryShake() {
        String str = null;
        for(int i = 0 ;i < 10000;i++){
            str += String.valueOf(i);
            Log.v("MYTAG","str:"+str);
        }
    }

    @DebugLog
    public void handlerLeak() {
        mMyHandler = new MyHandler();
        Message obtain = Message.obtain();
        obtain.arg1 = 1000;
        mMyHandler.sendMessageDelayed(obtain,1000 * 10);
    }
    //非静态内部类导致的内存泄漏
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("MYTAG","arg1"+msg.arg1);
        }
    }
    //HuGo测试
    @DebugLog
    public String getName(String first, String last) {
        SystemClock.sleep(15); // Don't ever really do this!
        return first + " " + last;
    }
    //严格模式测试StrickMode
    public void writeFile() {
        new Thread(){
            @Override
            public void run() {
                File externalStorage = Environment.getExternalStorageDirectory();
                File destFile = new File(externalStorage, "dest111333.txt");
                try {
                    if(!destFile.exists()){
                        destFile.createNewFile();
                    }
                    OutputStream output = new FileOutputStream(destFile, true);
                    output.write("droidyue.com".getBytes());
                    output.flush();
                    output.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
