package com.cwnu.ttpodmusic.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.cwnu.ttpodmusic.adapter.SongsAdapter;
import com.cwnu.ttpodmusic.entity.Songs;
import com.cwnu.ttpodmusic.utils.Constat;
import com.cwnu.ttpodmusic.utils.JsonUtil;
import com.cwnu.ttpodmusic.utils.OkHttpUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class SearchFragment extends Fragment {

	View view = null;


	private ImageView imgSearchDelete;

	private EditText etSearchDelete;

	private TextView tvSearch;

	private ListView lvSearch;

	private List<Songs> list;


	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 0x001:
					if(list!=null){
						Log.i(TAG, "handleMessage: "+list.size());
						SongsAdapter adapter = new SongsAdapter(getActivity(),list);
						lvSearch.setAdapter(adapter);
					}
					break;
				case 0x002:
					Toast.makeText(getActivity(),"没有找到歌曲，请换一个搜索词吧^_^",Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	public SearchFragment() {


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_search, container, false);
		setupView();
		addListener();

		return view;
	}

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

					imgSearchDelete.setVisibility(View.GONE);
				} else {

					imgSearchDelete.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}

			@Override
			public void afterTextChanged(Editable s) {}
		});
		// 给"搜索"textView添加监听
		tvSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etSearchDelete.getText().toString().trim() != null) {
					Toast.makeText(getActivity(), "searching", Toast.LENGTH_SHORT).show();
					new Thread(new Runnable() {
						public void run() {
							getSongsInfo(etSearchDelete.getText().toString().trim());
						}
					}).start();
				} else {
					Toast.makeText(getActivity(), "please input context", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//给ListView添加点击事件
		lvSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
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
	}

	private String getSongsInfo(final String song){
		String result = "";
		FormBody body = new FormBody.Builder()
					.add("hlpretag","")
					.add("hlposttag", "")
					.add("s",song)
					.add("type","1")
					.add("offset","0")
					.add("total","true")
					.add("limit","20")
					.build();
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(Constat.SEARCH_SONG_URL)
				.post(body)
				.build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.i(TAG, "onFailure: 发送的请求错误");

				Message msg = new Message();
				msg.what = 0x002;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String result = response.body().string();		// 获取返回体
				Log.i(TAG, "onResponse: "+result);
				JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();

				list = JsonUtil.getSongsList(jsonObject);
				Message msg;
				// 通知主线程更新界面
				msg = new Message();
				msg.what = 0x001;
				mHandler.sendMessage(msg);
			}
		});
		return result;
	}




}
