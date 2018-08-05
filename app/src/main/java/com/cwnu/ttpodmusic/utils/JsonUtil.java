package com.cwnu.ttpodmusic.utils;

import com.cwnu.ttpodmusic.entity.Songs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/8/5.
 * json 解析工具类
 */

public class JsonUtil {

    /**
     * 解析搜索的歌曲信息
     * @param jsonObject 通过OKhttp返回来的消息
     * @return  返回一个list<Song>
     */
    public static List<Songs> getSongsList(JsonObject jsonObject){
        List<Songs> list = new ArrayList<Songs>();



        int songCount = jsonObject.get("result").getAsJsonObject().get("songCount").getAsInt();
        if(songCount==0){
            list.add(new Songs(0,"无","无","无",0));
            return list;
        }
        JsonArray jsonArray = jsonObject.get("result").getAsJsonObject().get("songs").getAsJsonArray();     // 获取song列表

        for(int i=0;i<jsonArray.size();i++){
            Songs songs = new Songs();
            JsonObject item = jsonArray.get(i).getAsJsonObject();
            songs.setName(item.get("name").getAsString());
            songs.setId(item.get("id").getAsInt());
            songs.setArtist(item.get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
            songs.setArtistId(item.get("artists").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt());
            list.add(songs);
        }
        return list;
    }


}
