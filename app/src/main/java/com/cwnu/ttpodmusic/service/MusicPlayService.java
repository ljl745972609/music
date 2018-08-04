package com.cwnu.ttpodmusic.service;

import java.io.File;
import java.util.ArrayList;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.os.IBinder;

/**
 * Service:Android四大核心组件之一 主要实现歌曲的播放，暂停，上一首，下一首，seekBar的操作等耗时操作
 * 
 * @author DELL
 * 
 */
public class MusicPlayService extends Service {

	// 歌曲资源
	private ArrayList<String> musics;
	// 媒体播放工具
	private MediaPlayer player;
	// 歌曲所在的SD卡路径
	private File dir = null;
	// 注册广播接收器的对象
	private BroadcastReceiver receiver;
	// 当前歌曲的下标
	private int currentMusicIndex = 0;
	// 暂停位置
	private int pausePosition;
	// 控制流程的布尔类型变量
	private boolean isRunning;
	//布尔类型变量判断歌曲是否正在播放
	private boolean isStarted;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		player = new MediaPlayer();
		dir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		receiver = new MyActivityReceiver();
		// 广播过滤器
		IntentFilter filter = new IntentFilter();
		// 根据外号来进行过滤
		filter.addAction("CurrentMusicIndex");
		filter.addAction("PlayMusicPrevious");
		filter.addAction("PlayMusicNext");
		filter.addAction("MakeMusicPlayOrPause");
		filter.addAction("CurrentMusicPosition");
		// 注册广播接收器
		registerReceiver(receiver, filter);
		//开启工作线程发送广播，每隔一秒发送一次广播
		isRunning = true;
		new UpdateProgressThread().start();
		//循环播放
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				//播放下一首
				if(isStarted){
					next();
				}
				
			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// 获取intent里面传递过来的歌曲
		musics = intent.getStringArrayListExtra("musics");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	// 播放
	public void play() {
		
		// 重置播放工具
		player.reset();
		// 设置播放资源
		try {
			player.setDataSource(dir + "/" + musics.get(currentMusicIndex));
			// 准备播放
			player.prepare();
			// 移动到暂停位置
			player.seekTo(pausePosition);
			// 开始播放
			player.start();
			isStarted = true;
			// 发送广播，告诉Activity 去改变按钮的状态为暂停
			Intent intent = new Intent("setImagePause");
			sendBroadcast(intent);

			// 发送广播，携带歌曲名称和歌曲总时长
			Intent intent1 = new Intent("MusicName&MusicTotalTime");
			intent1.putExtra("MusicName", musics.get(currentMusicIndex)
					.toString());
			intent1.putExtra("MusicTotalTime", player.getDuration());
			sendBroadcast(intent1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 暂停
	public void pause() {
		if (player.isPlaying()) {
			player.pause();
			// 获取歌曲暂停位置
			pausePosition = player.getCurrentPosition();
			// 发送广播，改变播放按钮图标 设置播放则为三角符号
			Intent intent = new Intent("SetImgPlay");
			sendBroadcast(intent);

		}
	}

	// 上一首
	public void previous() {
		currentMusicIndex--;
		if (currentMusicIndex < 0) {
			currentMusicIndex = musics.size() - 1;
		}
		pausePosition = 0;
		play();

	}

	// 下一首
	public void next() {
		currentMusicIndex++;
		if (currentMusicIndex > musics.size() - 1) {
			currentMusicIndex = 0;
		}
		pausePosition = 0;
		play();
	}

	// 广播接收器。专门接收Activity接收来的广播
	class MyActivityReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// 接收广播(接收所有过滤到的广播，就是字符串)
			String action = intent.getAction();
			if ("CurrentMusicIndex".equals(action)) {
				// 取到携带的歌曲的下标 取不到时候播放第0首歌
				currentMusicIndex = intent.getIntExtra("position", 0);
				pausePosition = 0;
				// 播放歌曲
				play();
			} else if ("PlayMusicPrevious".equals(action)) {
				// 播放上一首
				previous();
			} else if ("PlayMusicNext".equals(action)) {
				// 播放下一首
				next();
			} else if ("MakeMusicPlayOrPause".equals(action)) {
				// 使音乐播放或者暂停
				if (player.isPlaying()) {
					pause();
				} else {
					play();
				}
			}else if ("CurrentMusicPosition".equals(action)) {
				//拿到seekbar停止后的位置
				int percent = intent.getIntExtra("seekbarPosition", 0);
				pausePosition = percent * player.getDuration()/100;
				//发送广播
				Intent intent1 = new Intent("seekToPausePosition");
				intent1.putExtra("seekPostion",pausePosition);
				sendBroadcast(intent1);
				if(player.isPlaying()){
					play();
				}else {
					pause();
				}
			}
			
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 接触注册广播接收器
		unregisterReceiver(receiver);
	}

	// 开启工作线程。每隔一秒钟发送一次当前歌曲位置的广播
	class UpdateProgressThread extends Thread {
		Intent intent = new Intent("UpdateProgress");

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRunning) {
				if (player.isPlaying()) {
					// 每隔一秒钟发送一次广播
					try {
						Thread.sleep(1000);
						// 携带数据，当前歌曲播放的为爱
						intent.putExtra("currentPosition",
								player.getCurrentPosition());
						sendBroadcast(intent);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			super.run();
		}

	}
}
