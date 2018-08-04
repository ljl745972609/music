package com.cwnu.ttpodmusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.cwnu.ttpodmusic.FileActivity;
import com.cwnu.ttpodmusic.MusicPlayActivity;
import com.cwnu.ttpodmusic.MyMusicActivity;
import com.cwnu.ttpodmusic.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class MineFragment extends Fragment {

	View view = null;
	// �����ؼ�
	private RelativeLayout rlOOne, rlOTwo, rlOThree;
	private RelativeLayout rlTOne, rlTTwo, rlTThree;
	private RelativeLayout rlThOne, rlThTwo, rlThThree;
	private RelativeLayout rlFOne, rlFTwo, rlFThree;

	public MineFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_mine, container, false);
		// ��ʼ���ؼ�
		setupView();
		// ��ȡ�ֻ���Ļ�Ŀ��
		WindowManager wm = getActivity().getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		// ����һ�еĵ�һ�����ÿ�Ⱥ͸߶�(ViewGrop��)
		LayoutParams lpOO = rlOOne.getLayoutParams();
		lpOO.width = width / 3 - 2;
		lpOO.height = width / 3 - 2;
		// ����һ�еĵڶ������ÿ�Ⱥ͸߶�
		LayoutParams lpOT = rlOTwo.getLayoutParams();
		lpOT.width = width / 3 - 2;
		lpOT.height = width / 3 - 2;
		// ����һ�еĵ��������ÿ�Ⱥ͸߶�
		LayoutParams lpOTh = rlOThree.getLayoutParams();
		lpOTh.width = width / 3 - 2;
		lpOTh.height = width / 3 - 2;

		// ���ڶ��еĵ�һ�����ÿ�Ⱥ͸߶�(ViewGrop��)
		LayoutParams lpTO = rlTOne.getLayoutParams();
		lpTO.width = width / 3 - 2;
		lpTO.height = width / 3 - 2;
		// ����һ�еĵڶ������ÿ�Ⱥ͸߶�
		LayoutParams lpTT = rlTTwo.getLayoutParams();
		lpTT.width = width / 3 - 2;
		lpTT.height = width / 3 - 2;
		// ����һ�еĵ��������ÿ�Ⱥ͸߶�
		LayoutParams lpTTh = rlTThree.getLayoutParams();
		lpTTh.width = width / 3 - 2;
		lpTTh.height = width / 3 - 2;

		// �������еĵ�һ�����ÿ�Ⱥ͸߶�(ViewGrop��)
		LayoutParams lpThO = rlThOne.getLayoutParams();
		lpThO.width = width / 3 - 2;
		lpThO.height = width / 3 - 2;
		// ����һ�еĵڶ������ÿ�Ⱥ͸߶�
		LayoutParams lpThT = rlThTwo.getLayoutParams();
		lpThT.width = width / 3 - 2;
		lpThT.height = width / 3 - 2;
		// ����һ�еĵ��������ÿ�Ⱥ͸߶�
		LayoutParams lpThTh = rlThThree.getLayoutParams();
		lpThTh.width = width / 3 - 2;
		lpThTh.height = width / 3 - 2;

		// �������еĵ�һ�����ÿ�Ⱥ͸߶�(ViewGrop��)
		LayoutParams lpFO = rlFOne.getLayoutParams();
		lpFO.width = width / 3 - 2;
		lpFO.height = width / 3 - 2;
		// ����һ�еĵڶ������ÿ�Ⱥ͸߶�
		LayoutParams lpFT = rlFTwo.getLayoutParams();
		lpFT.width = width / 3 - 2;
		lpFT.height = width / 3 - 2;
		// ����һ�еĵ��������ÿ�Ⱥ͸߶�
		LayoutParams lpFF = rlFThree.getLayoutParams();
		lpFF.width = width / 3 - 2;
		lpFF.height = width / 3 - 2;

		return view;
	}

	private void setupView() {
		rlOOne = (RelativeLayout) view.findViewById(R.id.rl_one_one);
		rlOTwo = (RelativeLayout) view.findViewById(R.id.rl_one_two);
		rlOThree = (RelativeLayout) view.findViewById(R.id.rl_one_three);

		rlTOne = (RelativeLayout) view.findViewById(R.id.rl_two_one);
		rlTTwo = (RelativeLayout) view.findViewById(R.id.rl_two_two);
		rlTThree = (RelativeLayout) view.findViewById(R.id.rl_two_three);

		rlThOne = (RelativeLayout) view.findViewById(R.id.rl_three_one);
		rlThTwo = (RelativeLayout) view.findViewById(R.id.rl_three_two);
		rlThThree = (RelativeLayout) view.findViewById(R.id.rl_three_three);

		rlFOne = (RelativeLayout) view.findViewById(R.id.rl_four_one);
		rlFTwo = (RelativeLayout) view.findViewById(R.id.rl_four_two);
		rlFThree = (RelativeLayout) view.findViewById(R.id.rl_four_three);
		//���ҵ�������ӵ���¼�
		rlOOne.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(getActivity(),MusicPlayActivity.class);
				Intent intent = new Intent(getActivity(), MyMusicActivity.class);
				startActivity(intent);
				
			}
		});
		rlOThree.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),FileActivity.class);
					startActivity(intent);
				}
			});
	}
}
