package com.cwnu.ttpodmusic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ��Ŀ������
 * @author DELL
 *
 */
public class Global {

	//��ʽ�ַ���
	public static String setTime(int time){
		//��ʽ�ַ�������
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		//�Ѻ���ֵת��ΪData����
		Date date = new Date(time);
		String str = sdf.format(date);
		return str;
	}


}
