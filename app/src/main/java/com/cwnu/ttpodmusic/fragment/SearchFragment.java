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
	// �����ؼ�
	ArrayAdapter<String> adapter = null;
	// ɾ��ͼƬ
	private ImageView imgSearchDelete;
	// �༭��
	private EditText etSearchDelete;
	// "����"�ı�
	private TextView tvSearch;

	// ����Ƥ
	private ListView lvSearch;
	// ����Դ
	ArrayList<String> musics = null;
	// ��ȡ�������ڵ�·��
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
		/*//�㲥������
		IntentFilter filter = new IntentFilter();*/
		//ע��㲥������
		/*registerReceiver(receiver, filter);*/
		return view;
	}

	// ��ɾ��ͼƬ��Ӽ���
	private void addListener() {
		
		imgSearchDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��EditText��������Ϊ��
				etSearchDelete.setText("");
				// ��ListView����
				lvSearch.setVisibility(View.GONE);
			}
		});
		// EditText��Ӽ���
		etSearchDelete.addTextChangedListener(new TextWatcher() {
			// �ı��ı�
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// �������Ϊ0
				if (s.length() == 0) {
					// ���ء�ɾ��ͼƬ��
					imgSearchDelete.setVisibility(View.GONE);
				} else {
					// ��ʾ��ɾ��ͼƬ��
					imgSearchDelete.setVisibility(View.VISIBLE);
					// ��ʾListView
					ShowListView();
				}
				
				adapter.getFilter().filter(s);
				
			}

			private void ShowListView() {
				// TODO Auto-generated method stub

			}

			// �ı��ı�֮ǰִ��
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			// �ı��ı�֮��ִ��
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		// ��"����"textView��Ӽ���
		tvSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etSearchDelete.getText().toString().trim() != null) {
					Toast.makeText(getActivity(), "���ڲ���", Toast.LENGTH_SHORT)
							.show();
				} else {

					Toast.makeText(getActivity(), "��������", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		//��ListView��ӵ���¼�
		lvSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// ���͹㲥��Я����ǰ�����и������±� CurrentMusicIndex �㲥���Լ�ȡ
				Intent intent = new Intent("CurrentMusicIndex");
				//Я�����ݣ������±꣩
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
		// ����etSearchDelete�����ʾ����ɫ
		etSearchDelete.setHintTextColor(999999);

		// ������
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

		// �Դ�����Ƥandroid.R.layout lists:������
		adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_1, musics);
		// �����¹�
		lvSearch.setAdapter(adapter);
	}
	class MyFragementReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
