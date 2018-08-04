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

	// 声明控件
	private ViewPager vpWelcome;
	// 数据源
	int[] imgs = { R.drawable.wel1, R.drawable.wel2, R.drawable.wel3, };
	//進入按鈕
	private ImageButton btnWelcom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		// 初始化控件
		setupView();
		FileUtil.createDrec();

		Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
		startActivity(intent);
	}

	private void setupView() {
		vpWelcome = (ViewPager) findViewById(R.id.vp);
		
		btnWelcom = (ImageButton) findViewById(R.id.btn_welcome);
		btnWelcom.setVisibility(View.GONE);
		//饺子下锅
		MyPagerAdapter adapter = new MyPagerAdapter();
		vpWelcome.setAdapter(adapter);
		
		//給ViewPager添加監聽
		vpWelcome.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				//判断是否是最后一页，若是则显示按钮
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
		
		
		//給按鈕添加監聽
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
	
	
	
	
	
	//Adapter适配器
	class MyPagerAdapter extends PagerAdapter{

		//获取页数
		@Override
		public int getCount() {
			return imgs.length;
		}

		//当前要显示的页面是否来自数据源中的某一页
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		//获取某一个页面
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			//显示图片的控件
			ImageView img = new ImageView(WelcomeActivity.this);
			//设置控件的大小
			img.setScaleType(ScaleType.FIT_XY);   //设置自动缩放
			//把数据源设置到控件上
			img.setImageResource(imgs[position]);
			//把控件放到布局中
			container.addView(img);
			Log.i("TAG","初始化"+position);
			return img;
		}
		//销毁页面(滑出屏幕的，并且跟屏幕不响铃的哪一个)
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//父类对象转化为子类
			Log.i("TAG","销毁"+position);
			container.removeView((View)object);
		}
	}

}
