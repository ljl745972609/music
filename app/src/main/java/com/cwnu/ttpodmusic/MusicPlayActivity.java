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

	// �����ؼ�
	private ListView lv;
	//����Img
	private ImageView img;
	// ����Դ
	ArrayList<String> musics = null;
	// ��ȡ�������ڵ�·��
	private File dir = null;
	private BroadcastReceiver receiver;
	//���Ż���ͣ��ť
	private ImageButton imbPlay;
	//��һ�װ�ť
	private ImageButton imbPrevious;
	//��һ�װ�ť
	private ImageButton imbNext;
	//��������
	private TextView tvMusicName;
	//������ʱ��
	private TextView tvTotalTime;
	//������ǰʱ��
	private TextView tvCurrentTime;
	//������
	private SeekBar sb;
	//�໬�˵�
	private ListView lvDrawer;
	private ImageView imgDrawer;
	//���벼��
	private DrawerLayout drawer;
	//����
	private ImageView imgListSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_play);
		setupView();
		// ��Ӽ�����
		addListener();
		receiver = new MyServiceReceiver();
		//�㲥������
		IntentFilter filter = new IntentFilter();
		filter.addAction("setImagePause");
		filter.addAction("SetImgPlay");
		filter.addAction("MusicName&MusicTotalTime");
		filter.addAction("UpdateProgress");
		filter.addAction("seekToPausePosition");
		//ע��㲥������
		registerReceiver(receiver, filter);
	}

	private void addListener() {
		// TODO Auto-generated method stub
		imgListSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("TAG","��������");
				
			}
		});
		//��������Ӽ����¼�
		lvDrawer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//�رճ���
				drawer.closeDrawer(lvDrawer);
				switch (position) {
				case 2:
					//ʵ�ֻ���
					drawer.setBackgroundResource(imgs[getThemeBg()]);
					lvDrawer.setBackgroundResource(imgs[index]);
					break;
				case 1:
					//����ģʽ
					Intent intent = new Intent(MusicPlayActivity.this,PlayWayActivity.class);
					startActivity(intent);
					break;
				case 4:
					//����ģʽ
					Intent intent1 = new Intent(MusicPlayActivity.this,SettingActivity.class);
					startActivity(intent1);
					break;
				case 6:
					//����ģʽ
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
				//�򿪳���
				drawer.openDrawer(lvDrawer);
				
			}
		});
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub  //Ҫ���Ƿ���
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
				//���͹㲥��Serviceȥ������һ��
				Intent intent = new Intent("PlayMusicPrevious");
				sendBroadcast(intent);
			}
		});
		imbNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//���͹㲥��Serviceȥ������һ��
				Intent intent = new Intent("PlayMusicNext");
				sendBroadcast(intent);
			}
		});
		imbPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//���͹㲥����Service���Ż�����ͣ
				Intent intent = new Intent("MakeMusicPlayOrPause");
				sendBroadcast(intent);
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// ���͹㲥��Я����ǰ�����и������±� CurrentMusicIndex �㲥���Լ�ȡ
				Intent intent = new Intent("CurrentMusicIndex");
				//Я�����ݣ������±꣩
				intent.putExtra("position", position);
				sendBroadcast(intent);
			}
		});
		// ��ImageView��ӻ��˹���
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
		// ���и����ļ�
		File[] files = dir.listFiles(); // �о�dir���������ļ�
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				musics.add(files[i].getName());
			}
		}
		// �����¹�
		MyAdapter adapter = new MyAdapter(musics, this);
		lv.setAdapter(adapter);
		// ��������ȥִ�к�ʱ����
		Intent intent = new Intent(MusicPlayActivity.this,
				MusicPlayService.class);
		// Я������
		intent.putStringArrayListExtra("musics", musics);
		startService(intent);
		
		//�໬�˵�������Դ
		String[] strsDrawer = {"��¼","����ģʽ","���Ի���","����","����","�˳���¼","����","��������"};
		//Adapter������
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strsDrawer);
		//�����¹�
		lvDrawer.setAdapter(adapter1);
		//���ñ���
		drawer.setBackgroundResource(imgs[index]);
		lvDrawer.setBackgroundResource(imgs[index]);
	}
	//�㲥��������ר�Ž���Service�����Ĺ㲥
	class MyServiceReceiver extends BroadcastReceiver{

		int totalTime = 0;
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			//�õ����й��˵��Ĺ㲥
			String action = intent.getAction();
			if("setImagePause".equals(action)){
				//�ı䰴ťͼ��Ϊ��ͣ
				imbPlay.setImageResource(android.R.drawable.ic_media_pause);
			}else if ("SetImgPlay".equals(action)) {
				//�ı䰴ťͼ��Ϊ����
				imbPlay.setImageResource(android.R.drawable.ic_media_play);
			}else if ("MusicName&MusicTotalTime".equals(action)) {
				//��ȡ��������
				String musicName = intent.getStringExtra("MusicName");
				tvMusicName.setText("���ڲ��ţ�"+musicName);
				//��ȡ������ʱ��
				totalTime = intent.getIntExtra("MusicTotalTime", 1);
				String strTotalTime = Global.setTime(totalTime);
				//��ʾ������ʱ��
				tvTotalTime.setText(strTotalTime);
			}else if ("UpdateProgress".equals(action)) {
				//ÿ��һ������ʾһ�ε�ǰʱ��
				int currentTime = intent.getIntExtra("currentPosition", 0);
				String strCurrentTime = Global.setTime(currentTime);
				//��ʾʱ��
				tvCurrentTime.setText(strCurrentTime);
				//���ý�����
				//��ȡ������λ��
				if (totalTime != 0) {
					int percent = currentTime*100/totalTime;
					//����seekbar����
					sb.setProgress(percent++);
					
				}
				
			}else if ("seekToPausePosition".equals(action)) {
				//ȡ����ǰʱ�䣬��������
				int currenttime = intent.getIntExtra("seekPostion", 0);
				String strCurrentTime = Global.setTime(currenttime);
				//��ʾʱ��
				tvCurrentTime.setText(strCurrentTime);
			}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//�Ӵ�ע��
		unregisterReceiver(receiver);
	}
	//�õ����������ͼƬ��Դ
	int[] imgs = {
			R.drawable.backgroud,R.drawable.bg1,R.drawable.bg2
	};
	//������±�
	private static int index;
	//���������Ƥ�����±�
	public int getThemeBg(){
		Random random = new Random();
		index = random.nextInt(imgs.length);
		Intent intent = new Intent("ThemeBg");
		intent.putExtra("index", index);
		sendBroadcast(intent);
		return index;
	}
}
