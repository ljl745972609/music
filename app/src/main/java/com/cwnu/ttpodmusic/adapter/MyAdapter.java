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
 * ��������
 * ����Դ��item������ϣ�BaseAdapter�����ࣩ
 * @author DELL
 *
 */
public class MyAdapter extends BaseAdapter{

	//����Դ
	private ArrayList<String> musics;
	//item����
	private LayoutInflater inflate;  //��ͼ������ �ò����ļ�
	public MyAdapter(ArrayList<String> musics,Context context){
		this.musics = musics;   //this.musicsָ��private List<String> musics;   musicsָ�����music
		this.inflate = LayoutInflater.from(context);
	}
	//��ȡitem����
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

	//��ȡĳһ��item�б������ĳһ�����ӣ�
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			convertView = inflate.inflate(R.layout.item_layout, null);
			holder = new ViewHolder();
			//�õ����õ�item�����ϵĿؼ�
			holder.tvMusicName = (TextView) convertView.findViewById(R.id.tv_item_musicname);
			//��convertView���һ�����
			convertView.setTag(holder);
		}else {
			//�����ֱ�Ӹ��ݱ������ʹ��
			holder = (ViewHolder) convertView.getTag();
		}
		//������Դ����Ҫ��ʾ�Ŀؼ���
		holder.tvMusicName.setText(musics.get(position));
		return convertView;
	}
	//���������
	class ViewHolder{
		private TextView tvMusicName;
	}
}
