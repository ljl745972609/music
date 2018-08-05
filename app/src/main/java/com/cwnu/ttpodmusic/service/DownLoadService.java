package com.cwnu.ttpodmusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.cwnu.ttpodmusic.utils.Constat;
import com.cwnu.ttpodmusic.utils.FileUtil;
import com.cwnu.ttpodmusic.utils.OkHttpUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.BufferOverflowException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by dell on 2018/8/5.
 * 下载服务
 */

public class DownLoadService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            Log.i(TAG, "onStartCommand: bundle is not null");
            final int id = bundle.getInt("id");
            final String name = bundle.getString("song");
            //Log.i(TAG, "onStartCommand: "+id+"\t"+name);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Call call = OkHttpUtil.sendGet(Constat.DOWNLOAD_URL+id+".mp3");
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // 发送下载失败广播
                            send(Constat.DOWNLOAD_FAIL_FLAG);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            byte[] bytes = response.body().bytes();
                            int i = FileUtil.writerFile(FileUtil.MUSIC,name,bytes);
                            if(i==1){
                                Log.i(TAG, "onResponse: 下载成功");
                            }
                            stopSelf();     // 关闭下载线程

                        }
                    });
                }
            }).start();

        }
        Log.i(TAG, "onStartCommand: 开始下载");


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void send(String action){
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }
}
