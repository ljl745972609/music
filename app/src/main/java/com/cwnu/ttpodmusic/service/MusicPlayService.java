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
 * Service:Android�Ĵ�������֮һ ��Ҫʵ�ָ����Ĳ��ţ���ͣ����һ�ף���һ�ף�seekBar�Ĳ����Ⱥ�ʱ����
 * 
 * @author DELL
 * 
 */
public class MusicPlayService extends Service {

	// ������Դ
	private ArrayList<String> musics;
	// ý�岥�Ź���
	private MediaPlayer player;
	// �������ڵ�SD��·��
	private File dir = null;
	// ע��㲥�������Ķ���
	private BroadcastReceiver receiver;
	// ��ǰ�������±�
	private int currentMusicIndex = 0;
	// ��ͣλ��
	private int pausePosition;
	// �������̵Ĳ������ͱ���
	private boolean isRunning;
	//�������ͱ����жϸ����Ƿ����ڲ���
	private boolean isStarted;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		player = new MediaPlayer();
		dir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		receiver = new MyActivityReceiver();
		// �㲥������
		IntentFilter filter = new IntentFilter();
		// ������������й���
		filter.addAction("CurrentMusicIndex");
		filter.addAction("PlayMusicPrevious");
		filter.addAction("PlayMusicNext");
		filter.addAction("MakeMusicPlayOrPause");
		filter.addAction("CurrentMusicPosition");
		// ע��㲥������
		registerReceiver(receiver, filter);
		//���������̷߳��͹㲥��ÿ��һ�뷢��һ�ι㲥
		isRunning = true;
		new UpdateProgressThread().start();
		//ѭ������
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				//������һ��
				if(isStarted){
					next();
				}
				
			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// ��ȡintent���洫�ݹ����ĸ���
		musics = intent.getStringArrayListExtra("musics");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	// ����
	public void play() {
		
		// ���ò��Ź���
		player.reset();
		// ���ò�����Դ
		try {
			player.setDataSource(dir + "/" + musics.get(currentMusicIndex));
			// ׼������
			player.prepare();
			// �ƶ�����ͣλ��
			player.seekTo(pausePosition);
			// ��ʼ����
			player.start();
			isStarted = true;
			// ���͹㲥������Activity ȥ�ı䰴ť��״̬Ϊ��ͣ
			Intent intent = new Intent("setImagePause");
			sendBroadcast(intent);

			// ���͹㲥��Я���������ƺ͸�����ʱ��
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

	// ��ͣ
	public void pause() {
		if (player.isPlaying()) {
			player.pause();
			// ��ȡ������ͣλ��
			pausePosition = player.getCurrentPosition();
			// ���͹㲥���ı䲥�Ű�ťͼ�� ���ò�����Ϊ���Ƿ���
			Intent intent = new Intent("SetImgPlay");
			sendBroadcast(intent);

		}
	}

	// ��һ��
	public void previous() {
		currentMusicIndex--;
		if (currentMusicIndex < 0) {
			currentMusicIndex = musics.size() - 1;
		}
		pausePosition = 0;
		play();

	}

	// ��һ��
	public void next() {
		currentMusicIndex++;
		if (currentMusicIndex > musics.size() - 1) {
			currentMusicIndex = 0;
		}
		pausePosition = 0;
		play();
	}

	// �㲥��������ר�Ž���Activity�������Ĺ㲥
	class MyActivityReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// ���չ㲥(�������й��˵��Ĺ㲥�������ַ���)
			String action = intent.getAction();
			if ("CurrentMusicIndex".equals(action)) {
				// ȡ��Я���ĸ������±� ȡ����ʱ�򲥷ŵ�0�׸�
				currentMusicIndex = intent.getIntExtra("position", 0);
				pausePosition = 0;
				// ���Ÿ���
				play();
			} else if ("PlayMusicPrevious".equals(action)) {
				// ������һ��
				previous();
			} else if ("PlayMusicNext".equals(action)) {
				// ������һ��
				next();
			} else if ("MakeMusicPlayOrPause".equals(action)) {
				// ʹ���ֲ��Ż�����ͣ
				if (player.isPlaying()) {
					pause();
				} else {
					play();
				}
			}else if ("CurrentMusicPosition".equals(action)) {
				//�õ�seekbarֹͣ���λ��
				int percent = intent.getIntExtra("seekbarPosition", 0);
				pausePosition = percent * player.getDuration()/100;
				//���͹㲥
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
		// �Ӵ�ע��㲥������
		unregisterReceiver(receiver);
	}

	// ���������̡߳�ÿ��һ���ӷ���һ�ε�ǰ����λ�õĹ㲥
	class UpdateProgressThread extends Thread {
		Intent intent = new Intent("UpdateProgress");

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRunning) {
				if (player.isPlaying()) {
					// ÿ��һ���ӷ���һ�ι㲥
					try {
						Thread.sleep(1000);
						// Я�����ݣ���ǰ�������ŵ�Ϊ��
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
