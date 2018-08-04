package com.cwnu.ttpodmusic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cwnu.ttpodmusic.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 适配器类
 * 数据源和item布局组合（BaseAdapter抽象类）
 * @author DELL
 *
 */
public class MyAdapter extends BaseAdapter{

	//数据源
	private ArrayList<String> musics;
	//item布局
	private LayoutInflater inflate;  //视图扩充器 拿布局文件
	public MyAdapter(ArrayList<String> musics,Context context){
		this.musics = musics;   //this.musics指向private List<String> musics;   musics指向参数music
		this.inflate = LayoutInflater.from(context);
	}
	//获取item个数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musics.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	//获取某一个item列表项（包好某一个饺子）
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			convertView = inflate.inflate(R.layout.item_layout, null);
			holder = new ViewHolder();
			//拿到复用的item布局上的控件
			holder.tvMusicName = (TextView) convertView.findViewById(R.id.tv_item_musicname);
			//给convertView添加一个标记
			convertView.setTag(holder);
		}else {
			//如果有直接根据标记拿来使用
			holder = (ViewHolder) convertView.getTag();
		}
		//把数据源设置要显示的控件上
		holder.tvMusicName.setText(musics.get(position));
		return convertView;
	}
	//对象持有者
	class ViewHolder{
		private TextView tvMusicName;
	}
}
