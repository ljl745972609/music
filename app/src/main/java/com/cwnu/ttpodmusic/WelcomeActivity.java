package com.cwnu.ttpodmusic;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.cwnu.ttpodmusic.utils.FileUtil;

import java.io.File;

public class WelcomeActivity extends Activity {


	private ViewPager vpWelcome;

	int[] imgs = { R.drawable.wel1, R.drawable.wel2, R.drawable.wel3, };

	private ImageButton btnWelcom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		setupView();
		FileUtil.createDrec();

		Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
		startActivity(intent);
	}

	private void setupView() {
		vpWelcome = (ViewPager) findViewById(R.id.vp);
		
		btnWelcom = (ImageButton) findViewById(R.id.btn_welcome);
		btnWelcom.setVisibility(View.GONE);

		MyPagerAdapter adapter = new MyPagerAdapter();
		vpWelcome.setAdapter(adapter);
		

		vpWelcome.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub

				 if (position == imgs.length - 1){
				btnWelcom.setVisibility(View.VISIBLE);
				 }else {
				 btnWelcom.setVisibility(View.GONE);
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
		
		

		btnWelcom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(WelcomeActivity.this,"startActivity",Toast.LENGTH_LONG).show();
				Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
	
	
	
	
	
	//Adapter������
	class MyPagerAdapter extends PagerAdapter{

		//��ȡҳ��
		@Override
		public int getCount() {
			return imgs.length;
		}

		//��ǰҪ��ʾ��ҳ���Ƿ���������Դ�е�ĳһҳ
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		//��ȡĳһ��ҳ��
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub

			ImageView img = new ImageView(WelcomeActivity.this);

			img.setScaleType(ScaleType.FIT_XY);   //�����Զ�����
			//������Դ���õ��ؼ���
			img.setImageResource(imgs[position]);
			//�ѿؼ��ŵ�������
			container.addView(img);
			Log.i("TAG","��ʼ��"+position);
			return img;
		}
		//����ҳ��(������Ļ�ģ����Ҹ���Ļ���������һ��)
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//�������ת��Ϊ����
			Log.i("TAG","����"+position);
			container.removeView((View)object);
		}
	}

}
