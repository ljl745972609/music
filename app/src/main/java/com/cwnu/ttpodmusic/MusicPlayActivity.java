package com.cwnu.ttpodmusic;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cwnu.ttpodmusic.adapter.MyAdapter;
import com.cwnu.ttpodmusic.fragment.SearchFragment;
import com.cwnu.ttpodmusic.service.MusicPlayService;
import com.cwnu.ttpodmusic.utils.Global;

public class MusicPlayActivity extends Activity {

	// 声明控件
	private ListView lv;
	//回退Img
	private ImageView img;
	// 数据源
	ArrayList<String> musics = null;
	// 获取歌曲所在的路径
	private File dir = null;
	private BroadcastReceiver receiver;
	//播放或暂停按钮
	private ImageButton imbPlay;
	//上一首按钮
	private ImageButton imbPrevious;
	//下一首按钮
	private ImageButton imbNext;
	//歌曲名称
	private TextView tvMusicName;
	//歌曲总时长
	private TextView tvTotalTime;
	//歌曲当前时间
	private TextView tvCurrentTime;
	//进度条
	private SeekBar sb;
	//侧滑菜单
	private ListView lvDrawer;
	private ImageView imgDrawer;
	//抽屉布局
	private DrawerLayout drawer;
	//搜索
	private ImageView imgListSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_play);
		setupView();
		// 添加监听器
		addListener();
		receiver = new MyServiceReceiver();
		//广播过滤器
		IntentFilter filter = new IntentFilter();
		filter.addAction("setImagePause");
		filter.addAction("SetImgPlay");
		filter.addAction("MusicName&MusicTotalTime");
		filter.addAction("UpdateProgress");
		filter.addAction("seekToPausePosition");
		//注册广播接收器
		registerReceiver(receiver, filter);
	}

	private void addListener() {
		// TODO Auto-generated method stub
		imgListSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("TAG","正在搜索");
				
			}
		});
		//给抽屉添加监听事件
		lvDrawer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//关闭抽屉
				drawer.closeDrawer(lvDrawer);
				switch (position) {
				case 2:
					//实现换肤
					drawer.setBackgroundResource(imgs[getThemeBg()]);
					lvDrawer.setBackgroundResource(imgs[index]);
					break;
				case 1:
					//播放模式
					Intent intent = new Intent(MusicPlayActivity.this,PlayWayActivity.class);
					startActivity(intent);
					break;
				case 4:
					//播放模式
					Intent intent1 = new Intent(MusicPlayActivity.this,SettingActivity.class);
					startActivity(intent1);
					break;
				case 6:
					//播放模式
					Intent intent6 = new Intent(MusicPlayActivity.this,HelpActivity.class);
					startActivity(intent6);
					break;

				default:
					break;
				}
				
			}
		});
		imgDrawer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//打开抽屉
				drawer.openDrawer(lvDrawer);
				
			}
		});
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub  //要的是分子
				int percent = seekBar.getProgress();
				Intent intent = new Intent("CurrentMusicPosition");
				intent.putExtra("seekbarPosition", percent);
				sendBroadcast(intent);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		imbPrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//发送广播给Service去播放上一首
				Intent intent = new Intent("PlayMusicPrevious");
				sendBroadcast(intent);
			}
		});
		imbNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//发送广播给Service去播放下一首
				Intent intent = new Intent("PlayMusicNext");
				sendBroadcast(intent);
			}
		});
		imbPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//发送广播告诉Service播放或者暂停
				Intent intent = new Intent("MakeMusicPlayOrPause");
				sendBroadcast(intent);
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 发送广播，携带当前被点中歌曲的下标 CurrentMusicIndex 广播名自己取
				Intent intent = new Intent("CurrentMusicIndex");
				//携带数据（歌曲下标）
				intent.putExtra("position", position);
				sendBroadcast(intent);
			}
		});
		// 给ImageView添加回退功能
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(MusicPlayActivity.this,
						MainActivity.class);
				startActivity(intent2);
			}
		});
	}

	private void setupView() {
		// TODO Auto-generated method stub
		imgListSearch = (ImageView) findViewById(R.id.img_list_search);
		drawer = (DrawerLayout) findViewById(R.id.drawer);
		imgDrawer = (ImageView) findViewById(R.id.img_list_menu);
		lvDrawer = (ListView) findViewById(R.id.lv_drawer);
		sb = (SeekBar) findViewById(R.id.sb);
		tvCurrentTime = (TextView) findViewById(R.id.tv_list_currenttime);
		tvTotalTime = (TextView) findViewById(R.id.tv_list_totaltime);
		tvMusicName = (TextView) findViewById(R.id.tv_list_musicname);
		imbPlay = (ImageButton) findViewById(R.id.imb_list_play);
		imbPrevious = (ImageButton) findViewById(R.id.imb_list_previous);
		imbNext = (ImageButton) findViewById(R.id.imb_list_next);
		lv = (ListView) findViewById(R.id.lv_list_);
		img = (ImageView) findViewById(R.id.img_list_back);
		musics = new ArrayList<String>();
		dir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		// 所有歌曲文件
		File[] files = dir.listFiles(); // 列举dir里面所有文件
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				musics.add(files[i].getName());
			}
		}
		// 饺子下锅
		MyAdapter adapter = new MyAdapter(musics, this);
		lv.setAdapter(adapter);
		// 启动服务去执行耗时操作
		Intent intent = new Intent(MusicPlayActivity.this,
				MusicPlayService.class);
		// 携带数据
		intent.putStringArrayListExtra("musics", musics);
		startService(intent);
		
		//侧滑菜单的数据源
		String[] strsDrawer = {"登录","播放模式","个性换肤","分享","设置","退出登录","帮助","个人中心"};
		//Adapter适配器
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strsDrawer);
		//饺子下锅
		lvDrawer.setAdapter(adapter1);
		//设置背景
		drawer.setBackgroundResource(imgs[index]);
		lvDrawer.setBackgroundResource(imgs[index]);
	}
	//广播接收器，专门接收Service发来的广播
	class MyServiceReceiver extends BroadcastReceiver{

		int totalTime = 0;
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			//拿到所有过滤到的广播
			String action = intent.getAction();
			if("setImagePause".equals(action)){
				//改变按钮图标为暂停
				imbPlay.setImageResource(android.R.drawable.ic_media_pause);
			}else if ("SetImgPlay".equals(action)) {
				//改变按钮图标为播放
				imbPlay.setImageResource(android.R.drawable.ic_media_play);
			}else if ("MusicName&MusicTotalTime".equals(action)) {
				//获取歌曲名称
				String musicName = intent.getStringExtra("MusicName");
				tvMusicName.setText("正在播放："+musicName);
				//获取歌曲总时长
				totalTime = intent.getIntExtra("MusicTotalTime", 1);
				String strTotalTime = Global.setTime(totalTime);
				//显示歌曲总时长
				tvTotalTime.setText(strTotalTime);
			}else if ("UpdateProgress".equals(action)) {
				//每隔一秒钟显示一次当前时间
				int currentTime = intent.getIntExtra("currentPosition", 0);
				String strCurrentTime = Global.setTime(currentTime);
				//显示时间
				tvCurrentTime.setText(strCurrentTime);
				//设置进度条
				//获取进度条位置
				if (totalTime != 0) {
					int percent = currentTime*100/totalTime;
					//设置seekbar进度
					sb.setProgress(percent++);
					
				}
				
			}else if ("seekToPausePosition".equals(action)) {
				//取到当前时间，重新设置
				int currenttime = intent.getIntExtra("seekPostion", 0);
				String strCurrentTime = Global.setTime(currenttime);
				//显示时间
				tvCurrentTime.setText(strCurrentTime);
			}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//接触注册
		unregisterReceiver(receiver);
	}
	//拿到随机换肤的图片资源
	int[] imgs = {
			R.drawable.backgroud,R.drawable.bg1,R.drawable.bg2
	};
	//随机的下标
	private static int index;
	//随机到个性皮肤的下标
	public int getThemeBg(){
		Random random = new Random();
		index = random.nextInt(imgs.length);
		Intent intent = new Intent("ThemeBg");
		intent.putExtra("index", index);
		sendBroadcast(intent);
		return index;
	}
}
