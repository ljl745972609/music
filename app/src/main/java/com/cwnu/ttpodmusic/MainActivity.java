package com.cwnu.ttpodmusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.cwnu.ttpodmusic.fragment.MineFragment;
import com.cwnu.ttpodmusic.fragment.RecommendFragment;
import com.cwnu.ttpodmusic.fragment.SearchFragment;
import com.cwnu.ttpodmusic.fragment.TaoGeFragment;

public class MainActivity extends FragmentActivity {

	// �����ؼ�
	private ViewPager vpMain;
	private RadioGroup rgMain;
	private RadioButton rb;
	private BroadcastReceiver receiver;
	private RelativeLayout rlMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��ʼ���ؼ�
		setupView();
		// ��Ӽ�����
		addListener();
		receiver = new MyThemeBgReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("ThemeBg");
		//ע��㲥
		
		registerReceiver(receiver, filter);
	}

	private void addListener() {
		// ��RadioGroup��Ӽ�����
		rgMain.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_mine:
					vpMain.setCurrentItem(0);
					break;
				case R.id.rb_taoge:
					vpMain.setCurrentItem(1);
					break;
				case R.id.rb_search:
					vpMain.setCurrentItem(2);
					break;
				case R.id.rb_recommend:
					vpMain.setCurrentItem(3);
					break;
				default:
					break;
				}

			}
		});
		// ��ViewPager��Ӽ�����
		vpMain.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					rgMain.check(R.id.rb_mine);
					break;
				case 1:
					rgMain.check(R.id.rb_taoge);
					break;
				case 2:
					rgMain.check(R.id.rb_search);
					break;
				case 3:
					rgMain.check(R.id.rb_recommend);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setupView() {
		rlMain = (RelativeLayout) findViewById(R.id.rl_main);
		vpMain = (ViewPager) findViewById(R.id.vp_main);
		rgMain = (RadioGroup) findViewById(R.id.rg_main);
		// �����¹�
		MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
		vpMain.setAdapter(adapter);
	}

	// Adapter������
	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		// ��ȡĳһҳ�����õ�ĳһ�����ӣ�
		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			Fragment fm = null;
			switch (position) {
			case 0:
				fm = new MineFragment();
				break;
			case 1:
				fm = new TaoGeFragment();
				break;
			case 2:
				fm = new SearchFragment();
				break;
			case 3:
				fm = new RecommendFragment();
				break;

			default:
				break;
			}
			return fm;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}
	}
	//�õ����������ͼƬ��Դ
	int[] imgs = {
			R.drawable.backgroud,R.drawable.bg1,R.drawable.bg2
			
	};
	private static int index;
	//�㲥������
	class MyThemeBgReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if("ThemeBg".equals(action)){
				//ȡ������±�
				index = intent.getIntExtra("index", 0);
				rlMain.setBackgroundResource(imgs[index]);
				
			}
		}
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//���ע��
		unregisterReceiver(receiver);
	}
}
