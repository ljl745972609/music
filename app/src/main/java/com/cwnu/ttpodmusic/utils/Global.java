package com.cwnu.ttpodmusic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目工具类
 * @author DELL
 *
 */
public class Global {

	//格式字符串
	public static String setTime(int time){
		//格式字符串工具
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		//把毫秒值转化为Data对象
		Date date = new Date(time);
		String str = sdf.format(date);
		return str;
	}


}
