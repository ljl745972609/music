package com.cwnu.ttpodmusic.fragment;

import java.io.File;
import java.util.ArrayList;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwnu.ttpodmusic.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class SearchFragment extends Fragment {

	View view = null;
	// 声明控件
	ArrayAdapter<String> adapter = null;
	// 删除图片
	private ImageView imgSearchDelete;
	// 编辑框
	private EditText etSearchDelete;
	// "搜索"文本
	private TextView tvSearch;

	// 饺子皮
	private ListView lvSearch;
	// 数据源
	ArrayList<String> musics = null;
	// 获取歌曲所在的路径
	private File dir = null;
	private Object receiver;
	

	public SearchFragment() {
		// Required empty public constructor

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_search, container, false);
		setupView();
		addListener();
		
		Object filter;
		/*//广播过滤器
		IntentFilter filter = new IntentFilter();*/
		//注册广播接收器
		/*registerReceiver(receiver, filter);*/
		return view;
	}

	// 给删除图片添加监听
	private void addListener() {
		
		imgSearchDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把EditText内容设置为空
				etSearchDelete.setText("");
				// 把ListView隐藏
				lvSearch.setVisibility(View.GONE);
			}
		});
		// EditText添加监听
		etSearchDelete.addTextChangedListener(new TextWatcher() {
			// 文本改变
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 如果长度为0
				if (s.length() == 0) {
					// 隐藏“删除图片”
					imgSearchDelete.setVisibility(View.GONE);
				} else {
					// 显示“删除图片”
					imgSearchDelete.setVisibility(View.VISIBLE);
					// 显示ListView
					ShowListView();
				}
				
				adapter.getFilter().filter(s);
				
			}

			private void ShowListView() {
				// TODO Auto-generated method stub

			}

			// 文本改变之前执行
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			// 文本改变之后执行
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		// 给"搜索"textView添加监听
		tvSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etSearchDelete.getText().toString().trim() != null) {
					Toast.makeText(getActivity(), "正在查找", Toast.LENGTH_SHORT)
							.show();
				} else {

					Toast.makeText(getActivity(), "输入内容", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		//给ListView添加点击事件
		lvSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 发送广播，携带当前被点中歌曲的下标 CurrentMusicIndex 广播名自己取
				Intent intent = new Intent("CurrentMusicIndex");
				//携带数据（歌曲下标）
				intent.putExtra("position", position);
				 
			}
		});
	}

	private void setupView() {
		// TODO Auto-generated method stub
		imgSearchDelete = (ImageView) view.findViewById(R.id.img_search_delete);
		etSearchDelete = (EditText) view.findViewById(R.id.et_search);
		lvSearch = (ListView) view.findViewById(R.id.lv_search);
		tvSearch = (TextView) view.findViewById(R.id.tv_search_seacher);
		// 设置etSearchDelete光标显示的颜色
		etSearchDelete.setHintTextColor(999999);

		// 包饺子
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

		// 自带饺子皮android.R.layout lists:饺子馅
		adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_1, musics);
		// 饺子下锅
		lvSearch.setAdapter(adapter);
	}
	class MyFragementReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
