package com.cwnu.ttpodmusic.utils;

/**
 * Created by dell on 2018/8/4.
 * 放置一些常亮
 */

public interface Constat {
    String ROOTPATH = "/sdcard/Android/data/com.ljl.music";
    String SEARCH_SONG_URL = "http://music.163.com/api/search/get/web?csrf_token="; // 搜索歌曲使用的url
    String DOWNLOAD_URL = "http://music.163.com/song/media/outer/url?id=";


    // 广播过滤器
    String DOWNLOAD_FLAG = "download";

}
