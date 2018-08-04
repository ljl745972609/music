package com.cwnu.ttpodmusic;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cwnu.ttpodmusic.fragment.TopFragment;
import com.cwnu.ttpodmusic.utils.FileUtil;

import java.util.ArrayList;

public class MyMusicActivity extends Activity {
    private FrameLayout frame;
    private ListView musicList;
    private String[] list;



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x001:
                    // TODO 成功时候
                    if(list.length == 0){
                        list = new String[1];
                        list[0] = ("没有音乐，请去音乐大厅下载吧！！！");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyMusicActivity.this,android.R.layout.simple_dropdown_item_1line,list);
                    musicList.setAdapter(adapter);
                    break;
                case 0x002:
                    // TODO 失败
                    Toast.makeText(MyMusicActivity.this,"本地音乐加载失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        init();     // 初始化界面


        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment,new TopFragment("本地音乐")).commit();
        findMusic();        // 查找音乐

    }

    public void init(){
        frame = (FrameLayout) findViewById(R.id.fragment);
        musicList = (ListView) findViewById(R.id.music_list);

    }
    private void findMusic(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] music = FileUtil.findMusic();
                if(music == null){
                    Message message = mHandler.obtainMessage();
                    message.what = 0x002;
                    mHandler.sendMessage(message);
                    return;
                }
                list =music;
                System.out.println(list.length);
                Message msg = mHandler.obtainMessage();
                msg.what = 0x001;
                mHandler.sendMessage(msg);
            }
        }).start();
    }


}
